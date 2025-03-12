# Define the AWS region to deploy resources
variable "aws_region" {
  description = "AWS region to deploy resources" # Description of the variable
  type        = string                           # The type of the variable
  default     = "us-west-2"                      # Default value for the AWS region
}

variable "aws_account_id" {
  description = "AWS Account ID"
  type        = string
}

variable "state_bucket_prefix" {
  description = "Bucket prefix for terraform state bucket"
  type        = string
}

# Define tags to be applied to resources
variable "tags" {
  type        = map(string) # The type of the variable (map of strings)
  description = "Tags"      # Description of the variable
  default     = {}          # Default value (empty map)
}

# Define the environment (prod or nonprod)
variable "env" {
  description = "prod or nonprod" # Description of the variable
  type        = string            # The type of the variable
}

variable "admin_role_profile" {
  description = "The name of the AWS profile to use"
  type        = string
}

variable "app_name" {
  type        = string
  description = "Name of app"
}

variable "key_name" {
  description = "The name of the existing key pair to use for SSH access"
  type        = string
}

variable "cognito_client_callback_urls" {
  type        = list(string)
  description = "Cognito Client callback URLs"
}

variable "cognito_client_logout_urls" {
  type        = list(string)
  description = "Cognito Client Logout URLs"
}

variable "instance_count" {
  description = "Number of EC2 instances to create"
  type        = number
  default     = 2
}
