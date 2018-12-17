package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.views.processors.ListFormViewProcessor;
import com.foreach.across.modules.entity.views.processors.SortableTableRenderingViewProcessor;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.TicketType;
import com.foreach.across.samples.booking.application.domain.booking.web.backend.views.SeatExportViewProcessor;
import com.foreach.across.samples.booking.application.domain.booking.web.backend.views.SeatListViewProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SeatUiConfiguration implements EntityConfigurer
{
	private final SeatExportViewProcessor exportViewProcessor;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Seat.class )
		        .label( "seatNumber" )
		        .listView(
				        lvb -> lvb.postProcess( SortableTableRenderingViewProcessor.class, tbl -> tbl.setIncludeDefaultActions( false ) )
				                  .postProcess( ListFormViewProcessor.class, l -> l.setAddDefaultButtons( false ) )
				                  .viewProcessor( new SeatListViewProcessor() )
				                  .and( CommonViewConfigurations.withRowHighlighting( this::resolveSeatRowStyle ) )
		        )
		        .view(
				        "export",
				        vb -> vb.showProperties( "seatNumber", "ticketType", "booking.name" )
				                .viewProcessor( exportViewProcessor )
		        );
	}

	private Style resolveSeatRowStyle( Seat seat ) {
		if ( seat != null && seat.getTicketType() == TicketType.VIP ) {
			return Style.SUCCESS;
		}

		return Style.INFO;
	}
}
