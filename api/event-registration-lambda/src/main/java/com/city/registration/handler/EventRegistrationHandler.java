package com.city.registration.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.city.common.exception.ExceptionHandler;
import com.city.common.model.entity.EventRegistrationEntity;
import com.city.common.model.response.ErrorResponse;
import com.city.common.util.EventGenerator;
import com.city.registration.mapper.EventRegistrationMapper;
import com.city.registration.model.request.CreateEventRegistrationReq;
import com.city.registration.model.response.ErrorResp;
import com.city.registration.repository.EventRegistrationRepository;
import com.city.registration.repository.EventRegistrationRepositoryImpl;
import com.city.registration.service.ValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class EventRegistrationHandler
    implements RequestHandler<Map<String, Object>, Map<String, Object>> {
  private static final Logger logger = LoggerFactory.getLogger(EventRegistrationHandler.class);
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String DYNAMODB_ENV = System.getenv("DYNAMODB_ENV"); // Read env variable
  public static final DynamoDbClient DYNAMO_DB_CLIENT = createDynamoDbClient();
  private final EventRegistrationRepository repository;

  // Hardcode USER ID for demonstration purposes. Ideally this would be extracted from a JWT
  private static final String USER_ID = "1234567890";

  public EventRegistrationHandler() {
    this(DYNAMO_DB_CLIENT);
  }

  public EventRegistrationHandler(DynamoDbClient dynamoDbClient) {
    this.repository = new EventRegistrationRepositoryImpl(dynamoDbClient);
  }

  public EventRegistrationHandler(EventRegistrationRepository repository) {
    this.repository = repository;
  }

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
    logger.info("📥 Received request: {}", event);

    try {
      Map<String, Object> requestContext = (Map<String, Object>) event.get("requestContext");
      if (requestContext == null || !requestContext.containsKey("http")) {
        logger.error("❌ Invalid request: missing requestContext.http");
        return createResponse(400, "Invalid request");
      }

      Map<String, Object> httpInfo = (Map<String, Object>) requestContext.get("http");
      String httpMethod = (httpInfo != null) ? (String) httpInfo.get("method") : null;
      String path = (httpInfo != null) ? (String) httpInfo.get("path") : null;
      String body = (String) event.get("body");

      logger.info("➡️ HTTP Method: {}, Path: {}, Body: {}", httpMethod, path, body);

      if ("OPTIONS".equalsIgnoreCase(httpMethod)) {
        logger.info("⚙️ Handling CORS preflight request");
        return createCorsResponse();
      }

      Map<String, String> queryStringParameters =
          (Map<String, String>) event.get("queryStringParameters");
      String[] pathSegments = (path != null) ? path.split("/") : new String[0];
      String eventId = (pathSegments.length > 1) ? pathSegments[1] : null; // Extract eventId

      assert httpMethod != null;
      return switch (httpMethod) {
        case "POST" -> handlePost(body);
        case "GET" -> handleGetOne(eventId, queryStringParameters);
        case "DELETE" -> handleDelete(eventId, queryStringParameters);
        default -> {
          logger.warn("⚠️ Unsupported HTTP method: {}", httpMethod);
          yield createResponse(405, "Method Not Allowed");
        }
      };

    } catch (Exception e) {
      logger.error("💥 Exception in Lambda handler", e);
      return handleException(e);
    }
  }

  private Map<String, Object> handleGetOne(String eventId, Map<String, String> queryParams)
      throws JsonProcessingException {
    logger.info("🔍 Fetching registration for eventId={}, userId={}", eventId, USER_ID);

    if (eventId == null) {
      ErrorResp resp = new ErrorResp("MISSING_PARAM", "Missing eventId");
      return createResponse(400, OBJECT_MAPPER.writeValueAsString(resp));
    }

    Optional<EventRegistrationEntity> entity = repository.getRegistration(eventId, USER_ID);
    logger.debug("Found entity {}", entity);

    if (entity.isEmpty()) {
      logger.warn("📭 Event registration not found for eventId={}, userId={}", eventId, USER_ID);
      ErrorResp resp = new ErrorResp("NOT_FOUND", "Registration not found");
      return createResponse(404, OBJECT_MAPPER.writeValueAsString(resp));
    }

    return createResponse(
        200, OBJECT_MAPPER.writeValueAsString(EventRegistrationMapper.toResponse(entity.get())));
  }

  private Map<String, Object> handlePost(String body) throws JsonProcessingException {
    logger.info("📝 Processing new event registration: {}", body);

    CreateEventRegistrationReq request =
        OBJECT_MAPPER.readValue(body, CreateEventRegistrationReq.class);
    new ValidatorService<CreateEventRegistrationReq>().validate(request);

    boolean eventExists =
        EventGenerator.getEvents(Integer.MAX_VALUE).stream()
            .anyMatch(event -> event.getEventId().equals(request.getEventId()));

    if (!eventExists) {
      logger.warn("❌ Event not found: eventId={}", request.getEventId());
      ErrorResp resp = new ErrorResp("NOT_FOUND", "Event does not exist");
      return createResponse(404, OBJECT_MAPPER.writeValueAsString(resp));
    }

    EventRegistrationEntity entity = EventRegistrationMapper.toEntity(request);
    entity.setUserId(USER_ID);

    EventRegistrationEntity savedEntity = repository.saveRegistration(entity);

    logger.info(
        "✅ Event registration saved successfully: eventId={}, userId={}",
        savedEntity.getEventId(),
        savedEntity.getUserId());

    return createResponse(
        201, OBJECT_MAPPER.writeValueAsString(EventRegistrationMapper.toResponse(savedEntity)));
  }

  private Map<String, Object> handleDelete(String eventId, Map<String, String> queryParams)
      throws JsonProcessingException {
    logger.info("🗑️ Deleting event registration: eventId={}, userId={}", eventId, USER_ID);

    if (eventId == null) {
      ErrorResp resp = new ErrorResp("MISSING_PARAM", "Missing eventId");
      return createResponse(400, OBJECT_MAPPER.writeValueAsString(resp));
    }

    Optional<EventRegistrationEntity> existingRegistration =
        repository.getRegistration(eventId, USER_ID);
    if (existingRegistration.isEmpty()) {
      logger.warn("📭 Event registration not found: eventId={}, userId={}", eventId, USER_ID);
      return createResponse(404, "{\"message\": \"Event registration not found\"}");
    }

    boolean deleted = repository.deleteRegistration(eventId, USER_ID);

    if (!deleted) {
      logger.warn(
          "❌ Event registration not found for deletion: eventId={}, userId={}", eventId, USER_ID);
      return createResponse(404, "{\"message\": \"Event registration not found\"}");
    }

    logger.info(
        "✅ Event registration deleted successfully: eventId={}, userId={}", eventId, USER_ID);
    return createResponse(204, "");
  }

  private Map<String, Object> createResponse(int statusCode, String body) {
    Map<String, Object> response = new HashMap<>();
    response.put("statusCode", statusCode);
    response.put("headers", getCorsHeaders());
    response.put("body", body);
    return response;
  }

  private Map<String, Object> handleException(Exception e) {
    logger.error("💥 Exception caught: {}", e.getMessage(), e); // Log full details

    ErrorResponse errorResponse;
    if (e instanceof IllegalArgumentException) {
      errorResponse = ExceptionHandler.handleValidationException(e);
    } else {
      errorResponse = ExceptionHandler.handleException(e);
    }

    try {
      return createResponse(
          errorResponse.getStatusCode(), OBJECT_MAPPER.writeValueAsString(errorResponse));
    } catch (JsonProcessingException ex) {
      logger.error("❌ Failed to serialize error response", ex);
      return createResponse(500, "{\"message\": \"Internal Server Error\"}");
    }
  }

  private Map<String, String> getCorsHeaders() {
    return Map.of(
        "Content-Type", "application/json",
        "Access-Control-Allow-Origin", "*",
        "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS",
        "Access-Control-Allow-Headers", "Content-Type, Authorization");
  }

  private Map<String, Object> createCorsResponse() {
    logger.info("Returning CORS preflight response");

    Map<String, Object> response = new HashMap<>();
    response.put("statusCode", 204); // ✅ CORS preflight should return 204 No Content

    // ✅ Use a mutable HashMap instead of modifying an immutable map
    Map<String, String> headers = new HashMap<>(getCorsHeaders());
    headers.put("Access-Control-Allow-Credentials", "true"); // Some browsers require this

    response.put("headers", headers);
    response.put("body", ""); // ✅ No content required for preflight

    return response;
  }

  private static DynamoDbClient createDynamoDbClient() {
    if ("LOCAL".equalsIgnoreCase(DYNAMODB_ENV)) {
      return DynamoDbClient.builder()
          .region(Region.US_WEST_2) // Region doesn't matter for Local
          .endpointOverride(URI.create("http://local-dynamodb:8000")) // Local container name
          .credentialsProvider(
              StaticCredentialsProvider.create(
                  AwsBasicCredentials.create("fakeAccessKey", "fakeSecretKey"))) // Dummy creds
          .build();
    } else {
      return DynamoDbClient.builder()
          .region(Region.US_WEST_2) // Use real AWS region
          .credentialsProvider(DefaultCredentialsProvider.create()) // Uses IAM credentials
          .build();
    }
  }
}
