package com.foreach.across.samples.booking.application.domain.booking.web.backend.views;

import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.context.EntityViewContext;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.support.ViewElementBuilderMap;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.spring.security.actions.AllowableAction;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.builder.ContainerViewElementBuilderSupport;

import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.button;

public class BookingPopupViewProcessor extends EntityViewProcessorAdapter
{
	@Override
	protected void render( EntityViewRequest entityViewRequest,
	                       EntityView entityView,
	                       ContainerViewElementBuilderSupport<?, ?> containerBuilder,
	                       ViewElementBuilderMap builderMap,
	                       ViewElementBuilderContext builderContext ) {
		EntityViewContext entityViewContext = entityViewRequest.getEntityViewContext();

		if ( entityViewContext.getAllowableActions().contains( AllowableAction.READ ) ) {
			String detailView = entityViewContext.getLinkBuilder().forInstance( entityViewContext.getEntity() ).toUriString();
			containerBuilder.add( button().link( detailView ).style( Style.DEFAULT ).text( "View booking details" ) );
		}
	}
}
