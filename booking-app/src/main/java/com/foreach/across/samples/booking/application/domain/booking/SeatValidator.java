package com.foreach.across.samples.booking.application.domain.booking;

import com.foreach.across.modules.entity.validators.EntityValidatorSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class SeatValidator extends EntityValidatorSupport<Seat>
{
	private final SeatRepository seatRepository;

	@Override
	public boolean supports( Class<?> clazz ) {
		return Seat.class.isAssignableFrom( clazz );
	}

	@Override
	protected void postValidation( Seat seat, Errors errors, Object... validationHints ) {
		if ( !errors.hasFieldErrors( "seatNumber" ) ) {
			Seat existingSeat = seatRepository.findBySeatNumber( seat.getSeatNumber() );

			if ( existingSeat != null && !existingSeat.equals( seat ) ) {
				errors.rejectValue( "seatNumber", "alreadyAssigned" );
			}
		}
	}
}
