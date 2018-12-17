package com.foreach.across.samples.booking.application.domain.booking.web.backend.views;

import com.foreach.across.modules.bootstrapui.elements.Grid;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.modules.web.ui.elements.support.ContainerViewElementUtils;

import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.column;
import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.row;

public class BookingSummaryViewProcessor extends EntityViewProcessorAdapter
{
	@Override
	protected void postRender( EntityViewRequest entityViewRequest,
	                           EntityView entityView,
	                           ContainerViewElement container,
	                           ViewElementBuilderContext builderContext ) {
		container.addChild(
				row()
						.add( column( Grid.Device.MD.width( 4 ) ).name( "col-1" ) )
						.add( column( Grid.Device.MD.width( 4 ) ).name( "col-2" ) )
						.add( column( Grid.Device.MD.width( 4 ) ).name( "col-3" ) )
						.build( builderContext )
		);

		ContainerViewElementUtils.move( container, "invoice", "col-1" );
		ContainerViewElementUtils.move( container, "formGroup-followUp", "col-2" );
		ContainerViewElementUtils.move( container, "formGroup-seats", "col-3" );
	}
}
