# Lambda Function for events requests
resource "aws_lambda_function" "lambda_registration_events" {
  function_name = "${var.app_name}-registration-events-data-function"
  filename      = "../api/event-registration-lambda/target/event-registration-lambda-1.0.0.jar" # Java 21 Lambda function packaged as a JAR
  handler       = "com.city.registration.handler.EventRegistrationHandler::handleRequest"       # Java 21 handler format
  runtime       = "java21"
  memory_size   = 512 # Adjust memory as needed
  timeout       = 15  # Adjust timeout as needed
  role          = aws_iam_role.lambda_exec_role.arn

  environment {
    variables = {
      # DYNAMODB_TABLE_NAME = aws_dynamodb_table.events_data_table.name
      LAMBDA_AWS_REGION = "us-west-2"
    }
  }

  source_code_hash = filebase64sha256("../api/event-registration-lambda/target/event-registration-lambda-1.0.0.jar")
}

# Lambda Function URL
resource "aws_lambda_function_url" "lambda_registration_events_url" {
  function_name      = aws_lambda_function.lambda_registration_events.function_name
  authorization_type = "NONE" # This makes it public
}

# Permissions for the Lambda URL to allow public access
resource "aws_lambda_permission" "allow_public_invoke_registration" {
  statement_id           = "AllowPublicInvoke"
  action                 = "lambda:InvokeFunctionUrl"
  function_name          = aws_lambda_function.lambda_registration_events.function_name
  principal              = "*"
  function_url_auth_type = aws_lambda_function_url.lambda_registration_events_url.authorization_type
}

# IAM Policy for Lambda to perform CRUD operations on DynamoDB
resource "aws_iam_policy" "lambda_dynamodb_crud_policy" {
  name        = "${var.app_name}-lambda-dynamodb-crud"
  description = "IAM policy to allow Lambda to perform CRUD operations on DynamoDB table."

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "dynamodb:PutItem",
          "dynamodb:GetItem",
          "dynamodb:UpdateItem",
          "dynamodb:DeleteItem",
          "dynamodb:Query",
          "dynamodb:Scan"
        ]
        Resource = [
          "${aws_dynamodb_table.events_registration_data_table.arn}",
          "${aws_dynamodb_table.events_registration_data_table.arn}/*"
        ]
      }
    ]
  })
}

# Attach the policy to the Lambda execution role
resource "aws_iam_role_policy_attachment" "lambda_dynamodb_crud_policy_attachment" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = aws_iam_policy.lambda_dynamodb_crud_policy.arn
}

# Output the public URL for the Lambda function
output "lambda_registration_events_url" {
  description = "Public URL for the Lambda function (Events Registration)"
  value       = aws_lambda_function_url.lambda_registration_events_url.function_url
}
