package com.foreach.across.samples.booking.application.domain.booking.web.backend;

import com.foreach.across.modules.bootstrapui.elements.NumericFormElementConfiguration;
import com.foreach.across.modules.entity.actions.EntityConfigurationAllowableActionsBuilder;
import com.foreach.across.modules.entity.actions.FixedEntityAllowableActionsBuilder;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.registry.properties.EntityPropertySelector;
import com.foreach.across.modules.spring.security.actions.AllowableAction;
import com.foreach.across.modules.spring.security.actions.AuthorityMatchingAllowableActions;
import com.foreach.across.modules.spring.security.authority.AuthorityMatcher;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.BookingRepository;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class InvoiceUiConfiguration implements EntityConfigurer
{
	private final BookingRepository bookingRepository;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Invoice.class )
		        .allowableActionsBuilder( invoiceAllowableActionsBuilder() )
		        .properties(
				        props -> props.property( "amount" )
				                      .attribute( NumericFormElementConfiguration.class,
				                                  NumericFormElementConfiguration.currency( Currency.getInstance( "USD" ), 2, true ) )
				                      .and()
				                      .property( "booking" )
				                      .propertyType( Booking.class )
						        .<Invoice>valueFetcher( invoice -> !invoice.isNew() ? bookingRepository.findByInvoice( invoice ) : null )
						        .hidden( true )
		        )
		        .listView( lvb -> lvb.showProperties( "booking", EntityPropertySelector.CONFIGURED ) )
		        .updateFormView( fvb -> fvb.showProperties( "booking", EntityPropertySelector.CONFIGURED ) );
	}

	private EntityConfigurationAllowableActionsBuilder invoiceAllowableActionsBuilder() {
		Map<AllowableAction, AuthorityMatcher> actions = new HashMap<>();
		actions.put( AllowableAction.CREATE, AuthorityMatcher.allOf( "finance-department" ) );
		actions.put( AllowableAction.READ, AuthorityMatcher.allOf( "finance-department" ) );
		actions.put( AllowableAction.DELETE, AuthorityMatcher.allOf( "finance-department" ) );
		actions.put( AllowableAction.UPDATE, AuthorityMatcher.allOf( "finance-department" ) );

		return new FixedEntityAllowableActionsBuilder( AuthorityMatchingAllowableActions.forSecurityContext( actions ) );
	}
}
