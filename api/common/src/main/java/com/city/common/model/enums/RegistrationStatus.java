package com.city.common.model.enums;

/**
 * Enum representing possible registration statuses for an event.
 *
 * <p>NOTE: This enum is not currently used but could be leveraged in the future for enhanced
 * registration logic, such as tracking user intent, waitlists, or attendance status.
 */
public enum RegistrationStatus {
  GOING("GN"),
  NOT_GOING("NG"),
  INTERESTED("IN"),
  TENTATIVE("TN"),
  WAITLISTED("WL"),
  CHECKED_IN("CI"),
  CANCELLED("CN");

  private final String displayName;

  RegistrationStatus(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
