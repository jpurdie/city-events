package com.city.event;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.city.common.model.entity.EventEntity;
import com.city.common.util.EventGenerator;
import com.city.event.mapper.EventMapper;
import com.city.event.model.response.EventRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {
  private static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper(); // JSON serializer/deserializer
  private static final int DEFAULT_COUNT = 10; // Default number of events if not specified

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
    // Extract request context from the event payload
    Map<String, Object> requestContext = (Map<String, Object>) event.get("requestContext");
    if (requestContext == null || !requestContext.containsKey("http")) {
      return createResponse(400, "Invalid request"); // Return 400 if request context is missing
    }

    // Extract HTTP method from the request context
    Map<String, Object> httpInfo = (Map<String, Object>) requestContext.get("http");
    String httpMethod = (httpInfo != null) ? (String) httpInfo.get("method") : null;

    // Extract query parameters from the event
    Map<String, String> queryParams = (Map<String, String>) event.get("queryStringParameters");
    int eventCount = parseEventCount(queryParams); // Parse the event count from query parameters

    // Handle CORS preflight requests
    if ("OPTIONS".equalsIgnoreCase(httpMethod)) {
      return createCorsResponse();
    }

    // Handle GET request to fetch events
    if ("GET".equalsIgnoreCase(httpMethod)) {
      return handleGetRequest(eventCount);
    }

    // Return 405 (Method Not Allowed) if HTTP method is unsupported
    return createResponse(405, "{\"error\": \"Method Not Allowed\"}");
  }

  private Map<String, Object> handleGetRequest(int eventCount) {
    try {
      // Generate a list of events
      List<EventEntity> events = EventGenerator.getEvents(eventCount);
      // Convert events to response format
      List<EventRes> eventRespList = EventMapper.toResponseList(events);

      // Serialize and return the response
      return createResponse(200, serialize(eventRespList));
    } catch (JsonProcessingException e) {
      // Return 500 (Internal Server Error) in case of serialization failure
      return createResponse(500, "{\"error\": \"Internal Server Error\"}");
    }
  }

  private int parseEventCount(Map<String, String> queryParams) {
    if (queryParams != null && queryParams.containsKey("count")) {
      try {
        int count = Integer.parseInt(queryParams.get("count"));
        return count > 0 ? count : DEFAULT_COUNT; // Ensure a positive number, default to 10
      } catch (NumberFormatException e) {
        return DEFAULT_COUNT; // Default if parsing fails
      }
    }
    return DEFAULT_COUNT; // Default if parameter is missing
  }

  private String serialize(Object obj) throws JsonProcessingException {
    return OBJECT_MAPPER.writeValueAsString(obj); // Convert object to JSON string
  }

  private Map<String, Object> createResponse(int statusCode, String body) {
    Map<String, Object> response = new HashMap<>();
    response.put("statusCode", statusCode); // HTTP status code
    response.put("headers", getCorsHeaders()); // Include CORS headers
    response.put("body", body); // Response body
    return response;
  }

  private Map<String, Object> createCorsResponse() {
    Map<String, Object> response = new HashMap<>();
    response.put("statusCode", 204); // HTTP 204 No Content for CORS preflight
    Map<String, String> headers = new HashMap<>(getCorsHeaders());
    headers.put("Access-Control-Allow-Credentials", "true"); // Allow credentials for CORS
    response.put("headers", headers);
    response.put("body", ""); // No body needed for preflight response
    return response;
  }

  private Map<String, String> getCorsHeaders() {
    return Map.of(
        "Content-Type", "application/json",
        "Access-Control-Allow-Origin", "*", // Allow all origins
        "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS", // Allowed methods
        "Access-Control-Allow-Headers", "Content-Type, Authorization" // Allowed headers
        );
  }
}
