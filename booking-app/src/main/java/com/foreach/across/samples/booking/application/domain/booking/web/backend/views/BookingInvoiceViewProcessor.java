package com.foreach.across.samples.booking.application.domain.booking.web.backend.views;

import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.SingleEntityFormViewProcessor;
import com.foreach.across.modules.entity.views.request.EntityViewCommand;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.entity.web.links.EntityViewLinkBuilder;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.modules.web.ui.elements.support.ContainerViewElementUtils;
import org.springframework.validation.BindingResult;

public class BookingInvoiceViewProcessor extends EntityViewProcessorAdapter
{
	@Override
	protected void doPost( EntityViewRequest entityViewRequest, EntityView entityView, EntityViewCommand command, BindingResult bindingResult ) {
		if ( !bindingResult.hasErrors() ) {
			EntityViewLinkBuilder linkBuilder = entityViewRequest.getEntityViewContext().getLinkBuilder();
			entityView.setRedirectUrl(
					linkBuilder.forInstance( command.getEntity() )
					           .updateView()
					           .withViewName( "invoice" )
					           .toUriString()
			);
		}
	}

	@Override
	protected void postRender( EntityViewRequest entityViewRequest,
	                           EntityView entityView,
	                           ContainerViewElement container,
	                           ViewElementBuilderContext builderContext ) {
		ContainerViewElementUtils.move( container, "formGroup-followUp", SingleEntityFormViewProcessor.RIGHT_COLUMN );
	}
}
