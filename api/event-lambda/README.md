# Event Handler Lambda Function

## Overview
This AWS Lambda function provides a simple API for retrieving mock event data.  
It is designed to be invoked via an **AWS Lambda Function URL** and returns event information in JSON format.  

## Technology Stack
- **Java 21** - The latest LTS version of Java, ensuring performance and security.  
- **Maven** - Dependency and build management for the project.  
- **AWS Lambda** - Serverless execution for scalable event handling.  
- **Jackson** - JSON serialization and deserialization.  

## Functionality
- **Supports GET requests** to return a list of mock events.  
- **Handles CORS** for cross-origin requests.  
- **Supports query parameters** to specify the number of events to return.  
- **Returns JSON responses** containing event data.  

## API Usage

### Endpoint
The function is deployed as an **AWS Lambda Function URL**.  
Example:  
