package com.foreach.across.samples.booking.application.domain.booking;

import com.foreach.across.modules.hibernate.business.EntityWithDto;
import com.foreach.across.samples.modules.invoice.domain.invoice.Invoice;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * A single show booking.
 */
@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class Booking implements Persistable<Long>, EntityWithDto<Booking>
{
	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Length(max = 255)
	private String name;

	@NotBlank
	@Email
	@Length(max = 255)
	private String email;

	@Min(1)
	@Max(10)
	@Builder.Default
	private int numberOfTickets = 1;

	@NotNull
	private TicketType ticketType;

	private ZonedDateTime created;

	@OneToOne
	private Invoice invoice;

	@Override
	public Booking toDto() {
		return toBuilder().build();
	}

	@Override
	public boolean isNew() {
		return id == null;
	}
}
