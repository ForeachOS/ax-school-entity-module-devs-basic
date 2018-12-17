package com.foreach.across.samples.booking.application.domain.booking.web.backend.views;

import com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders;
import com.foreach.across.modules.bootstrapui.elements.builder.ColumnViewElementBuilder;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.processors.ExtensionViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.SingleEntityFormViewProcessor;
import com.foreach.across.modules.entity.views.processors.support.ViewElementBuilderMap;
import com.foreach.across.modules.entity.views.request.EntityViewCommand;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.modules.web.ui.elements.builder.ContainerViewElementBuilderSupport;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.*;

public class BookingEmailViewProcessor extends ExtensionViewProcessorAdapter<BookingEmailViewProcessor.EmailForm>
{
	@Override
	protected EmailForm createExtension( EntityViewRequest entityViewRequest, EntityViewCommand command, WebDataBinder dataBinder ) {
		EmailForm emailForm = new EmailForm();
		emailForm.setTo( entityViewRequest.getEntityViewContext().getEntity( Booking.class ).getEmail() );
		return emailForm;
	}

	@Override
	protected void doPost( EmailForm email, BindingResult bindingResult, EntityView entityView, EntityViewRequest entityViewRequest ) {
		if ( !bindingResult.hasErrors() ) {
			email.setSent( true );
		}
	}

	@Override
	protected void render( EmailForm email,
	                       EntityViewRequest entityViewRequest,
	                       EntityView entityView,
	                       ContainerViewElementBuilderSupport<?, ?> containerBuilder,
	                       ViewElementBuilderMap builderMap,
	                       ViewElementBuilderContext builderContext ) {
		builderMap.get( SingleEntityFormViewProcessor.LEFT_COLUMN, ColumnViewElementBuilder.class )
		          .add(
				          formGroup()
						          .label( "Subject" )
						          .required()
						          .control( textbox().controlName( controlPrefix() + ".subject" ).text( email.getSubject() ) )
		          )
		          .add(
				          formGroup()
						          .label( "Body" )
						          .control( textarea().controlName( controlPrefix() + ".body" ).text( email.getBody() ) )
		          );
	}

	@Override
	protected void postRender( EmailForm email,
	                           EntityViewRequest entityViewRequest,
	                           EntityView entityView,
	                           ContainerViewElement container,
	                           ViewElementBuilderContext builderContext ) {
		if ( email.isSent() ) {
			container.clearChildren();
			container.addChild(
					BootstrapUiBuilders.alert()
					                   .success()
					                   .text( "Email " + email.getSubject() + " has been sent to " + email.getTo() + "." )
					                   .build( builderContext )
			);
		}
	}

	@Data
	static class EmailForm
	{
		private String to;
		@NotBlank
		private String subject;
		private String body;
		private boolean sent;
	}
}
