# IAM Role for Lambda
resource "aws_iam_role" "lambda_exec_role" {
  name = "lambda_exec_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Action = "sts:AssumeRole"
      Effect = "Allow"
      Principal = {
        Service = "lambda.amazonaws.com"
      }
    }]
  })
}

# Attach basic Lambda execution policy
resource "aws_iam_role_policy_attachment" "lambda_basic_execution" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

# Lambda Function for events requests
resource "aws_lambda_function" "lambda_events" {
  function_name = "${var.app_name}-events-data-function"
  filename      = "../api/event-lambda/target/event-lambda-1.0.0.jar" # Java 21 Lambda function packaged as a JAR
  handler       = "com.city.event.EventHandler::handleRequest"        # Java 21 handler format
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

  source_code_hash = filebase64sha256("../api/event-lambda/target/event-lambda-1.0.0.jar")
}

# Lambda Function URL
resource "aws_lambda_function_url" "lambda_events_url" {
  function_name      = aws_lambda_function.lambda_events.function_name
  authorization_type = "NONE" # This makes it public
}

# Permissions for the Lambda URL to allow public access
resource "aws_lambda_permission" "allow_public_invoke_all" {
  statement_id           = "AllowPublicInvoke"
  action                 = "lambda:InvokeFunctionUrl"
  function_name          = aws_lambda_function.lambda_events.function_name
  principal              = "*"
  function_url_auth_type = aws_lambda_function_url.lambda_events_url.authorization_type
}

# Output the public URL for the Lambda function
output "lambda_events_url" {
  description = "Public URL for the Lambda function (Events)"
  value       = aws_lambda_function_url.lambda_events_url.function_url
}
