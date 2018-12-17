package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.Style;
import com.foreach.across.modules.entity.config.builders.EntityViewFactoryBuilder;
import com.foreach.across.samples.booking.application.domain.booking.web.backend.views.RowHighlightingViewProcessor;
import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
public class CommonViewConfigurations
{
	public static <U, V extends EntityViewFactoryBuilder> Consumer<V> withRowHighlighting( Function<U, Style> styleResolver ) {
		return vfb -> vfb.viewProcessor( new RowHighlightingViewProcessor<>( styleResolver ) );
	}
}
