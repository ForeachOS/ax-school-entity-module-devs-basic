package com.foreach.across.samples.booking.application.domain.booking.web.frontend;

import com.foreach.across.modules.entity.registry.properties.EntityPropertySelector;
import com.foreach.across.modules.entity.views.EntityViewElementBuilderHelper;
import com.foreach.across.modules.entity.views.ViewElementMode;
import com.foreach.across.modules.entity.views.helpers.EntityViewElementBatch;
import com.foreach.across.modules.web.template.Template;
import com.foreach.across.modules.web.ui.ViewElement;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZonedDateTime;
import java.util.Map;

import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.container;

@Controller
@Template(FrontendLayout.TEMPLATE)
@RequestMapping("/")
@RequiredArgsConstructor
class BookingController
{
	private final BookingRepository bookingRepository;
	private final EntityViewElementBuilderHelper entityViewElementBuilderHelper;

	@GetMapping
	String bookingForm( @ModelAttribute("booking") Booking booking, Model model ) {
		EntityViewElementBatch<Booking> batch = entityViewElementBuilderHelper.createBatchForEntity( booking );
		batch.setViewElementMode( ViewElementMode.FORM_WRITE );
		batch.setPropertySelector( EntityPropertySelector.of( "name", "email", "ticketType", "numberOfTickets" ) );
		Map<String, ViewElement> fieldsByName = batch.build();

		model.addAttribute( "formFields", container().addAll( fieldsByName.values() ).build() );

		return "th/booking/frontend/bookingForm";
	}

	@GetMapping("/thankyou")
	String bookingComplete() {
		return "th/booking/frontend/bookingComplete";
	}

	@PostMapping
	String submitBooking( @Validated @ModelAttribute("booking") Booking booking, BindingResult errors, Model model ) {
		if ( errors.hasErrors() ) {
			return bookingForm( booking, model );
		}

		booking.setCreated( ZonedDateTime.now() );

		bookingRepository.save( booking );

		return "redirect:/thankyou";
	}
}
