package com.city.registration.mapper;

import com.city.common.model.entity.EventRegistrationEntity;
import com.city.registration.model.request.CreateEventRegistrationReq;
import com.city.registration.model.response.CreateEventRegistrationResp;
import java.time.Instant;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventRegistrationMapper {
  private static final Logger logger = LoggerFactory.getLogger(EventRegistrationMapper.class);

  public static EventRegistrationEntity toEntity(CreateEventRegistrationReq request) {
    if (request == null) {
      return null;
    }

    EventRegistrationEntity entity = new EventRegistrationEntity();
    entity.setEventRegistrationId(UUID.randomUUID().toString());
    entity.setEventId(request.getEventId());
    entity.setQtyAttending(String.valueOf(request.getQtyAttending()));
    entity.setCreatedAt(Instant.now());
    entity.setUpdatedAt(Instant.now());
    return entity;
  }

  public static CreateEventRegistrationResp toResponse(EventRegistrationEntity entity) {
    if (entity == null) {
      return null;
    }

    CreateEventRegistrationResp response = new CreateEventRegistrationResp();
    response.setQtyAttending(Integer.valueOf(entity.getQtyAttending()));
    response.setCreatedAt(entity.getCreatedAt().toString());
    response.setUpdatedAt(entity.getUpdatedAt().toString());
    response.setEventId(entity.getEventId());
    return response;
  }
}
