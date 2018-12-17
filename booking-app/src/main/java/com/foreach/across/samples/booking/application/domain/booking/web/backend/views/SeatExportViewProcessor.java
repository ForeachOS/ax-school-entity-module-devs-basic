package com.foreach.across.samples.booking.application.domain.booking.web.backend.views;

import com.foreach.across.modules.entity.registry.properties.EntityPropertyDescriptor;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.PropertyRenderingViewProcessor;
import com.foreach.across.modules.entity.views.request.EntityViewCommand;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.samples.booking.application.domain.booking.Seat;
import com.foreach.across.samples.booking.application.domain.booking.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SeatExportViewProcessor extends EntityViewProcessorAdapter
{
	private final SeatRepository seatRepository;

	@Override
	protected void doControl( EntityViewRequest entityViewRequest,
	                          EntityView entityView,
	                          EntityViewCommand command,
	                          BindingResult bindingResult,
	                          HttpMethod httpMethod ) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.valueOf( "text/csv" ) );
		headers.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.csv" );

		List<Seat> seats = seatRepository.findAll();
		Collection<EntityPropertyDescriptor> fields = PropertyRenderingViewProcessor.propertyDescriptors( entityView ).values();

		StringBuilder export = new StringBuilder();
		seats.forEach(
				seat -> export.append( fields.stream()
				                             .map( f -> f.getPropertyValue( seat ) )
				                             .map( Object::toString )
				                             .collect( Collectors.joining( "," ) ) )
				              .append( "\n" )
		);

		entityView.setResponseEntity( new ResponseEntity<>( export, headers, HttpStatus.OK ) );
		entityView.setShouldRender( false );
	}
}
