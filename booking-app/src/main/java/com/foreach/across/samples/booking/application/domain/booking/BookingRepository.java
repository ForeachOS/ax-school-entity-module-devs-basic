package com.foreach.across.samples.booking.application.domain.booking;

import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring DATA JPA repository for the {@link Booking}.
 */
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking>
{
	Booking findByInvoice( Invoice invoice );
}
