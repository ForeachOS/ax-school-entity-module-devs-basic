package com.foreach.across.samples.booking.application.domain.booking.web.backend.views;

import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.entity.views.EntityView;
import com.foreach.across.modules.entity.views.bootstrapui.util.SortableTableBuilder;
import com.foreach.across.modules.entity.views.processors.EntityViewProcessorAdapter;
import com.foreach.across.modules.entity.views.processors.SortableTableRenderingViewProcessor;
import com.foreach.across.modules.entity.views.processors.support.ViewElementBuilderMap;
import com.foreach.across.modules.entity.views.request.EntityViewRequest;
import com.foreach.across.modules.entity.views.util.EntityViewElementUtils;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class RowHighlightingViewProcessor<T> extends EntityViewProcessorAdapter
{
	private final Function<T, Style> styleResolver;

	@Override
	@SuppressWarnings("unchecked")
	protected void createViewElementBuilders( EntityViewRequest entityViewRequest, EntityView entityView, ViewElementBuilderMap builderMap ) {
		SortableTableBuilder tableBuilder = builderMap.get( SortableTableRenderingViewProcessor.TABLE_BUILDER, SortableTableBuilder.class );

		tableBuilder.valueRowProcessor( ( builderContext, row ) -> {
			T entity = (T) EntityViewElementUtils.currentEntity( builderContext );

			Style style = styleResolver.apply( entity );

			if ( style != null ) {
				row.addCssClass( style.getName() );
			}
		} );
	}
}
