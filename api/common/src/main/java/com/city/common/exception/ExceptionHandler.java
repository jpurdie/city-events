package com.city.common.exception;

import com.city.common.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

  private static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred.";
  private static final String VALIDATION_ERROR_MESSAGE = "Invalid request.";

  public static ErrorResponse handleException(Exception e) {
    return new ErrorResponse(GENERIC_ERROR_MESSAGE, 500);
  }

  public static ErrorResponse handleValidationException(Exception e) {
    return new ErrorResponse(e.getMessage(), 400);
  }
}
