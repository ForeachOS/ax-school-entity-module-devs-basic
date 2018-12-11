package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.adminweb.menu.AdminMenuEvent;
import com.foreach.across.modules.entity.EntityAttributes;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.query.EntityQueryConditionTranslator;
import com.foreach.across.modules.entity.registry.EntityAssociation;
import com.foreach.across.modules.entity.registry.properties.EntityPropertySelector;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Sort;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BookingUiConfiguration implements EntityConfigurer
{
	private final SeatRepository seatRepository;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Booking.class )
		        .attribute( EntityAttributes.LINK_TO_DETAIL_VIEW, true )
		        .properties(
				        props -> props.property( "seats" )
				                      .propertyType( TypeDescriptor.collection( List.class, TypeDescriptor.valueOf( Seat.class ) ) )
				                      .valueFetcher( seatRepository::findAllByBooking )
				                      .and()
				                      .property( "name" )
				                      .attribute( EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.ignoreCase() )
				                      .and()
				                      .property( "email" )
				                      .attribute( EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.ignoreCase() )
				                      .and()
				                      .property( "searchText" )
				                      .propertyType( String.class )
				                      .attribute( EntityQueryConditionTranslator.class, EntityQueryConditionTranslator.expandingOr( "name", "email" ) )
		        )
		        .listView(
				        lvb -> lvb.showProperties( EntityPropertySelector.CONFIGURED, "~ticketType" )
				                  .defaultSort( new Sort( Sort.Direction.DESC, "created" ) )
				                  .entityQueryFilter( eqf -> eqf.showProperties( "ticketType", "searchText" ).multiValue( "ticketType" ) )
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
		        )
		        .association(
				        as -> as.name( "seat.booking" )
				                .associationType( EntityAssociation.Type.EMBEDDED )
				                .parentDeleteMode( EntityAssociation.ParentDeleteMode.WARN )
		        )
		;
	}

	@EventListener
	public void makeStudioAcrossTopLevel( AdminMenuEvent menu ) {
		menu.group( "/entities/BookingApplicationModule" ).changePathTo( "/studioAcross" );
	}
}
