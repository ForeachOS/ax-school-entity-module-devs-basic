package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.adminweb.menu.AdminMenuEvent;
import com.foreach.across.modules.bootstrapui.elements.TextboxFormElement;
import com.foreach.across.modules.entity.EntityAttributes;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.query.EntityQueryConditionTranslator;
import com.foreach.across.modules.entity.registry.EntityAssociation;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyHandlingType;
import com.foreach.across.modules.entity.registry.properties.EntityPropertySelector;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyValidator;
import com.foreach.across.modules.entity.views.EntityViewCustomizers;
import com.foreach.across.modules.entity.views.ViewElementMode;
import com.foreach.across.modules.web.resource.WebResource;
import com.foreach.across.modules.web.resource.WebResourceRegistry;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.SeatRepository;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import com.foreach.across.samples.modules.invoice.domain.invoice.InvoiceRepository;
import com.foreach.across.samples.modules.invoice.domain.invoice.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Validator;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BookingUiConfiguration implements EntityConfigurer
{
	private final SeatRepository seatRepository;
	private final InvoiceRepository invoiceRepository;
	private final Validator entityValidator;

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
				                      .hidden( true )
				                      .and()
				                      .property( "invoice" )
				                      .hidden( true )
				                      .attribute( EntityPropertyHandlingType.class, EntityPropertyHandlingType.BINDER )
				                      .controller(
						                      c -> c.withTarget( Booking.class, Invoice.class )
						                            .createValueFunction( booking -> Invoice.builder()
						                                                                    .name( booking.getName() )
						                                                                    .email( booking.getEmail() )
						                                                                    .amount( booking.getNumberOfTickets() * 15.0 )
						                                                                    .invoiceStatus( InvoiceStatus.SENT )
						                                                                    .build() )
						                            .validator( EntityPropertyValidator.of( entityValidator ) )
						                            .saveConsumer( ( booking, invoice ) -> invoiceRepository.save( invoice.getNewValue() ) )
				                      )
				                      .and()
				                      .property( "invoice.invoiceStatus" )
				                      .writable( false )
				                      .and()
				                      .property( "followUp" )
				                      .hidden( true )
				                      .and()
				                      .property( "followUp[].remarks" )
				                      .viewElementPostProcessor( ViewElementMode.CONTROL, this::enableRichText )
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
		        .formView(
				        "invoice", EntityViewCustomizers.basicSettings()
				                                        .adminMenu( "/invoice" )
				                                        .andThen( fvb -> fvb.showProperties( "invoice.*", "followUp" ) )
		        )
		        .association(
				        as -> as.name( "seat.booking" )
				                .associationType( EntityAssociation.Type.EMBEDDED )
				                .parentDeleteMode( EntityAssociation.ParentDeleteMode.WARN )
		        )
		;
	}

	private void enableRichText( ViewElementBuilderContext builderContext, TextboxFormElement textbox ) {
		textbox.setAttribute( "rich-text", true );

		WebResourceRegistry webResourceRegistry = builderContext.getAttribute( WebResourceRegistry.class );
		webResourceRegistry.add( WebResource.JAVASCRIPT, "https://cdn.ckeditor.com/ckeditor5/11.1.1/classic/ckeditor.js", WebResource.EXTERNAL );
		webResourceRegistry.add( WebResource.JAVASCRIPT_PAGE_END, "/static/booking/js/rich-text.js", WebResource.VIEWS );
	}

	@EventListener
	public void makeStudioAcrossTopLevel( AdminMenuEvent menu ) {
		menu.group( "/entities/BookingApplicationModule" ).changePathTo( "/studioAcross" );
	}
}
