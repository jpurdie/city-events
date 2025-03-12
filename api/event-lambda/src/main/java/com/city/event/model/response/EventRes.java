package com.city.event.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EventRes {
  private String eventId;
  private String name;
  private String description;
  private String createdAt;
  private String updatedAt;
  private String startingAt;
  private String endingAt;
}
