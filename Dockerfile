# This Dockerfile is for local development use only.

# Use Amazon's official DynamoDB local image
FROM amazon/dynamodb-local:latest

# Set working directory inside container
WORKDIR /home/dynamodblocal

# Expose default DynamoDB Local port
EXPOSE 8000

# Start DynamoDB Local
CMD ["-jar", "DynamoDBLocal.jar", "-sharedDb"]
