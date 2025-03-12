# Event Registration Module

## Overview
This module handles **event registrations** for an event management system.  
It is designed to be used in an **AWS Lambda environment** and interacts with **Amazon DynamoDB** for data storage.

## Technology Stack
- **Java 21** - Latest LTS version for performance and security.  
- **Maven** - Dependency management and build tool.  
- **AWS SDK v2** - For interacting with Amazon DynamoDB.  
- **Jakarta Validation** - Ensures request data is valid.  
- **Jackson** - Handles JSON serialization/deserialization.  
- **SLF4J + Log4j2** - Logging framework for debugging and monitoring.  

## Features
- **Event Registration API**  
  - `POST /` → Register for an event.  
  - `GET /{eventId}` → Retrieve registration details.  
  - `DELETE /{eventId}` → Cancel registration.  
- **DynamoDB Integration**  
  - Stores and retrieves event registrations.  
- **Validation & Error Handling**  
  - Uses **Jakarta Validation** for request validation.  
  - Centralized exception handling.  

