package com.foreach.across.samples.booking.application.domain.booking;

import com.foreach.across.modules.hibernate.aop.EntityInterceptorAdapter;
import com.foreach.across.samples.modules.invoice.domain.invoice.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Takes care of deleting any attached seats and invoice before deleting the actual booking.
 */
@Component
@RequiredArgsConstructor
class BookingInterceptor extends EntityInterceptorAdapter<Booking>
{
	private final SeatRepository seatRepository;
	private final InvoiceRepository invoiceRepository;

	@Override
	public boolean handles( Class<?> entityClass ) {
		return Booking.class.isAssignableFrom( entityClass );
	}

	@Override
	public void beforeDelete( Booking booking ) {
		seatRepository.delete( seatRepository.findAllByBooking( booking ) );

		if ( booking.getInvoice() != null ) {
			invoiceRepository.delete( booking.getInvoice() );
		}
	}
}
