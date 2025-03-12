package com.city.registration.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEventRegistrationReq {

  @NotBlank(message = "eventId cannot be empty")
  private String eventId;

  @Positive(message = "qtyAttending cannot be less than 1")
  private int qtyAttending;
}
