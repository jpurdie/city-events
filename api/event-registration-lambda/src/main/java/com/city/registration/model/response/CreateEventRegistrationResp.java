package com.city.registration.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateEventRegistrationResp {
  private String eventId;
  private int qtyAttending;
  private String createdAt;
  private String updatedAt;
}
