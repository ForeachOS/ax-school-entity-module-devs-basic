package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.TicketType;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeatUiConfiguration implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Seat.class )
		        .label( "seatNumber" )
		        .listView( CommonViewConfigurations.withRowHighlighting( this::resolveSeatRowStyle ) );
	}

	private Style resolveSeatRowStyle( Seat seat ) {
		if ( seat != null && seat.getTicketType() == TicketType.VIP ) {
			return Style.SUCCESS;
		}

		return Style.INFO;
	}
}
