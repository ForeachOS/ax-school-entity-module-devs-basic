package com.foreach.across.samples.booking.application.domain.booking.web.frontend;

import com.foreach.across.modules.web.template.Template;
import com.foreach.across.samples.booking.application.domain.booking.Booking;
import com.foreach.across.samples.booking.application.domain.booking.BookingRepository;
import com.foreach.across.samples.booking.application.domain.booking.TicketType;
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

import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.formGroup;
import static com.foreach.across.modules.bootstrapui.elements.BootstrapUiBuilders.textbox;

@Controller
@Template(FrontendLayout.TEMPLATE)
@RequestMapping("/")
@RequiredArgsConstructor
class BookingController
{
	private final BookingRepository bookingRepository;

	@GetMapping
	String bookingForm( @ModelAttribute("booking") Booking booking, Model model ) {
		model.addAttribute( "ticketTypes", TicketType.values() );

		model.addAttribute(
				"nameField",
				formGroup()
						.label( "Name" )
						.control( textbox().controlName( "name" ).text( booking.getName() ) )
						.required()
						.build()
		);

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
