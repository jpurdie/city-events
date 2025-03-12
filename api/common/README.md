# Common AWS Lambda Module

## Overview
This is a **shared Maven module** designed for use across multiple AWS Lambda functions.  
It provides **common utilities, models, and configurations** to ensure consistency and reduce code duplication.

## Features
- **DynamoDB Client Factory** for seamless database interactions.  
- **Entity Models** representing key application objects.  
- **Error Handling** with standardized exception responses.  
- **Logging** using **Log4j2** for structured application logs.  

## Technology Stack
- **Java 21** - Latest LTS version, ensuring high performance and security.  
- **Maven** - Dependency and build management.  
- **AWS SDK v2** - Official AWS SDK for interacting with AWS services.  
- **Log4j2** - High-performance logging framework.  

## Modules

### 1. **DynamoDB Client Factory**
Provides a **singleton DynamoDB client**, automatically configured based on the AWS region.

### 2. **Entity Models**
Includes commonly used data models such as:  
- `EventEntity` - Represents an event object.  
- `EventRegistrationEntity` - Represents user registrations for events.  

### 3. **Response Models**
Standardized response objects for API responses:  
- `ErrorResponse` - Standard error messages with HTTP status codes.  

### 4. **Exception Handling**
Centralized exception handling with:  
- `ExceptionHandler` - Converts exceptions into structured responses.  

## Usage in AWS Lambda
To use this module in your AWS Lambda functions:  
1. **Add as a dependency in `pom.xml`**:
   ```xml
   <dependency>
       <groupId>com.city.common</groupId>
       <artifactId>common-module</artifactId>
       <version>1.0.0</version>
   </dependency>
