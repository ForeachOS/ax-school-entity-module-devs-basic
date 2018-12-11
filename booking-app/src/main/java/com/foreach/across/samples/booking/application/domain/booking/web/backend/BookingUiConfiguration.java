package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.adminweb.menu.AdminMenuEvent;
import com.foreach.across.modules.entity.EntityAttributes;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.registry.properties.EntityPropertySelector;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;

@Configuration
public class BookingUiConfiguration implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Booking.class )
		        .attribute( EntityAttributes.LINK_TO_DETAIL_VIEW, true )
		        .listView(
				        lvb -> lvb.showProperties( EntityPropertySelector.CONFIGURED, "~ticketType" )
				                  .defaultSort( new Sort( Sort.Direction.DESC, "created" ) )
		        )
		        .createFormView(
				        fvb -> fvb.properties(
						        props -> props.property( "created" )
						                      .attribute( EntityAttributes.PROPERTY_REQUIRED, true )
				        )
		        )
		        .updateFormView(
				        fvb -> fvb.properties( props -> props.property( "created" ).writable( false ) )
				                  .showProperties( EntityPropertySelector.WRITABLE, "created" )
		        );
	}

	@EventListener
	public void makeStudioAcrossTopLevel( AdminMenuEvent menu ) {
		menu.group( "/entities/BookingApplicationModule" ).changePathTo( "/studioAcross" );
	}
}
