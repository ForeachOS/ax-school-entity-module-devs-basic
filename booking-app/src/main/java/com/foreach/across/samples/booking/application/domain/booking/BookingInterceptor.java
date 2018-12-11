package com.foreach.across.samples.booking.application.domain.booking;

import com.foreach.across.modules.hibernate.aop.EntityInterceptorAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Takes care of deleting any attached seats before deleting the actual booking.
 */
@Component
@RequiredArgsConstructor
class BookingInterceptor extends EntityInterceptorAdapter<Booking>
{
	private final SeatRepository seatRepository;

	@Override
	public boolean handles( Class<?> entityClass ) {
		return Booking.class.isAssignableFrom( entityClass );
	}

	@Override
	public void beforeDelete( Booking booking ) {
		seatRepository.delete( seatRepository.findAllByBooking( booking ) );
	}
}
