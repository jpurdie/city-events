package com.city.event.mapper;

import com.city.common.model.entity.EventEntity;
import com.city.event.model.response.EventRes;
import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

  public static EventRes toResponse(EventEntity entity) {
    if (entity == null) {
      return null;
    }
    EventRes response = new EventRes();
    response.setEventId(entity.getEventId());
    response.setName(entity.getName());
    response.setDescription(entity.getDescription());
    response.setCreatedAt(entity.getCreatedAt().toString());
    response.setUpdatedAt(entity.getUpdatedAt().toString());
    response.setStartingAt(entity.getStartingAt().toString());
    response.setEndingAt(entity.getEndingAt().toString());
    return response;
  }

  public static List<EventRes> toResponseList(List<EventEntity> entities) {
    if (entities == null || entities.isEmpty()) {
      return List.of();
    }
    return entities.stream().map(EventMapper::toResponse).collect(Collectors.toList());
  }
}
