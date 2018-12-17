package com.foreach.across.samples.booking.application.domain.booking.web.backend.controls;

import com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders;
import com.foreach.across.modules.entity.views.util.EntityViewElementUtils;
import com.foreach.across.modules.entity.web.links.EntityViewLinks;
import com.foreach.across.modules.web.ui.ViewElementBuilder;
import com.foreach.across.modules.web.ui.ViewElementBuilderContext;
import com.foreach.across.modules.web.ui.elements.ContainerViewElement;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingLinkBuilder implements ViewElementBuilder<ContainerViewElement>
{
	private final EntityViewLinks entityViewLinks;

	@Override
	public ContainerViewElement build( ViewElementBuilderContext builderContext ) {
		Booking booking = EntityViewElementUtils.currentPropertyValue( builderContext, Booking.class );

		return BootstrapUiBuilders.container()
		                          .add(
				                          booking == null ? null :
						                          BootstrapUiBuilders.link()
						                                             .url( entityViewLinks.linkTo( booking ).toUriString() )
						                                             .text( booking.getName() )
		                          )
		                          .build( builderContext );
	}
}
