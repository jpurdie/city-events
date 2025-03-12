package com.city.common.model.entity;

import java.time.Instant;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRegistrationEntity {
  private String eventRegistrationId; // Unique ID
  private String eventId;
  private String userId;
  private String qtyAttending;
  private Instant updatedAt;
  private Instant createdAt;
}
