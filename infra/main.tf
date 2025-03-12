// main.tf

// Specify the required Terraform version and provider configurations
terraform {
  required_version = ">= 1.2.8" # Ensure Terraform version is at least 1.2.8

  // Define the required providers and their versions
  required_providers {
    aws = {
      source  = "hashicorp/aws" # Specify the AWS provider source
      version = ">= 5.90"
    }
    random = {
      source  = "hashicorp/random" # Specify the Random provider source
      version = ">= 3.6.0"         # Ensure Random provider version is at least 2.0
    }
  }
  backend "s3" {
    bucket         = "city-events-terraform-state-backup"
    key            = "tfstate/terraform.tfstate"
    region         = "us-west-2"
    encrypt        = true
    dynamodb_table = "city-events-terraform-locks"
  }
}
output "account_id" {
  value = data.aws_caller_identity.current.account_id
}

// Configure the AWS provider with the region specified in the variables
provider "aws" {
  region = var.aws_region # Set the AWS region to the value of the 'aws_region' variable
}

// Retrieve information about the current AWS account
data "aws_caller_identity" "current" {
}

