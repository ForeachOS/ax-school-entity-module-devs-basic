package com.foreach.across.samples.booking.application.domain.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat>
{
	List<Seat> findAllByBooking( Booking booking );

	Seat findBySeatNumber( String seatNumber );
}
