package com.city.registration.repository;

import com.city.common.model.entity.EventRegistrationEntity;
import java.time.Instant;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class EventRegistrationRepositoryImpl implements EventRegistrationRepository {
  private static final String TABLE_NAME = "city-events-events-registration";
  private final DynamoDbClient dynamoDbClient;
  private static final Logger logger =
      LoggerFactory.getLogger(EventRegistrationRepositoryImpl.class);

  public EventRegistrationRepositoryImpl(DynamoDbClient dynamoDbClient) {
    this.dynamoDbClient = dynamoDbClient;
  }

  @Override
  public EventRegistrationEntity saveRegistration(EventRegistrationEntity registration) {
    logger.info("Saving event registration: {}", registration);

    Map<String, AttributeValue> item = new HashMap<>();
    item.put("userId", AttributeValue.builder().s(registration.getUserId()).build()); // PK
    item.put("eventId", AttributeValue.builder().s(registration.getEventId()).build()); // SK
    item.put(
        "eventRegistrationId",
        AttributeValue.builder().s(registration.getEventRegistrationId()).build()); // Unique ID
    item.put("qtyAttending", AttributeValue.builder().s(registration.getQtyAttending()).build());
    item.put(
        "createdAt", AttributeValue.builder().s(registration.getCreatedAt().toString()).build());
    item.put(
        "updatedAt", AttributeValue.builder().s(registration.getUpdatedAt().toString()).build());

    PutItemRequest request = PutItemRequest.builder().tableName(TABLE_NAME).item(item).build();

    try {
      dynamoDbClient.putItem(request);
      logger.info("✅ Event registration saved: registration={}", registration);
    } catch (DynamoDbException e) {
      logger.error("❌ Failed to save registration: {}", e.getMessage(), e);
      throw e;
    }

    return registration;
  }

  @Override
  public Optional<EventRegistrationEntity> getRegistration(String eventId, String userId) {
    logger.info("Fetching registration: eventId={}, userId={}", eventId, userId);

    Map<String, AttributeValue> expressionValues = new HashMap<>();
    expressionValues.put(":userId", AttributeValue.builder().s(userId).build());
    expressionValues.put(":eventId", AttributeValue.builder().s(eventId).build());

    QueryRequest queryRequest =
        QueryRequest.builder()
            .tableName(TABLE_NAME)
            .keyConditionExpression("userId = :userId AND eventId = :eventId") // ✅ Matches PK + SK
            .expressionAttributeValues(expressionValues)
            .build();

    try {
      List<Map<String, AttributeValue>> items = dynamoDbClient.query(queryRequest).items();
      logger.debug("DynamoDB query response: {}", items);

      if (items.isEmpty()) {
        logger.warn("📭 No registration found for eventId={}, userId={}", eventId, userId);
        return Optional.empty();
      }

      return Optional.of(mapItemToEntity(items.get(0)));
    } catch (DynamoDbException e) {
      logger.error("❌ Failed to fetch registration: {}", e.getMessage(), e);
      throw e;
    }
  }

  @Override
  public boolean deleteRegistration(String eventId, String userId) {
    logger.info("🗑️ Deleting registration: eventId={}, userId={}", eventId, userId);

    Map<String, AttributeValue> key = new HashMap<>();
    key.put("userId", AttributeValue.builder().s(userId).build()); // PK
    key.put("eventId", AttributeValue.builder().s(eventId).build()); // SK

    DeleteItemRequest deleteRequest =
        DeleteItemRequest.builder().tableName(TABLE_NAME).key(key).build();

    try {
      dynamoDbClient.deleteItem(deleteRequest);
      logger.info("✅ Successfully deleted registration: eventId={}, userId={}", eventId, userId);
      return true;
    } catch (DynamoDbException e) {
      logger.error("❌ Failed to delete registration: {}", e.getMessage(), e);
      return false;
    }
  }

  private EventRegistrationEntity mapItemToEntity(Map<String, AttributeValue> item) {
    logger.debug("Mapping DynamoDB item to entity: {}", item);

    EventRegistrationEntity registration = new EventRegistrationEntity();

    if (item.containsKey("eventRegistrationId")) {
      registration.setEventRegistrationId(item.get("eventRegistrationId").s());
    } else {
      logger.warn("⚠️ eventRegistrationId is missing in DynamoDB response!");
    }

    registration.setEventId(item.get("eventId").s());
    registration.setUserId(item.get("userId").s());
    registration.setQtyAttending(item.get("qtyAttending").s());
    registration.setCreatedAt(Instant.parse(item.get("createdAt").s()));
    registration.setUpdatedAt(Instant.parse(item.get("updatedAt").s()));

    logger.debug("Mapped entity: {}", registration);
    return registration;
  }
}
