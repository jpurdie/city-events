package com.city.registration.repository;

import com.city.common.model.entity.EventRegistrationEntity;
import java.util.Optional;

public interface EventRegistrationRepository {

  EventRegistrationEntity saveRegistration(EventRegistrationEntity registration);

  Optional<EventRegistrationEntity> getRegistration(String eventId, String userId);

  boolean deleteRegistration(String eventId, String userId);
}
