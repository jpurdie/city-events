resource "random_pet" "s3_pet_name" {
  length = 2
}


resource "aws_s3_bucket" "app_client_bucket" {
  bucket = "${var.app_name}-${random_pet.s3_pet_name.id}-bucket" # Change this to a globally unique name
  tags = merge(
    var.tags
  )
}

// Configure the S3 bucket for website hosting
resource "aws_s3_bucket_website_configuration" "app_client_bucket_website" {
  bucket = aws_s3_bucket.app_client_bucket.bucket
  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "index.html" // SPA fallback to index.html
  }

}

// Manage public access block settings for the bucket
resource "aws_s3_bucket_public_access_block" "app_client_bucket_public_access_block" {
  bucket                  = aws_s3_bucket.app_client_bucket.id
  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}


resource "aws_s3_bucket_policy" "app_client_bucket_policy" {
  bucket = aws_s3_bucket.app_client_bucket.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect    = "Allow",
        Principal = "*",
        Action    = "s3:GetObject",
        Resource  = "${aws_s3_bucket.app_client_bucket.arn}/*"
      }
    ]
  })
}


// Sync build files to S3 bucket
resource "null_resource" "sync_build_files" {
  provisioner "local-exec" {
    command     = "aws s3 sync ../client/dist s3://${aws_s3_bucket.app_client_bucket.bucket}/ --delete --profile ${var.admin_role_profile}"
    working_dir = "${path.module}/../client"
  }
  depends_on = [
    aws_s3_bucket.app_client_bucket,
    aws_s3_bucket_website_configuration.app_client_bucket_website,
    aws_s3_bucket_policy.app_client_bucket_policy,
    aws_s3_bucket_public_access_block.app_client_bucket_public_access_block // Added dependency
  ]
}

/*
 Outputs
*/
output "s3_website_url" {
  value       = "http://${aws_s3_bucket.app_client_bucket.bucket}.s3-website-${var.aws_region}.amazonaws.com"
  description = "The URL of the S3 bucket configured for static website hosting"
}
output "website_endpoint" {
  value       = aws_s3_bucket_website_configuration.app_client_bucket_website.website_endpoint
  description = "aws_s3_bucket_website_configuration.app_client_bucket_website.website_endpoint"
}

output "website_domain" {
  value       = aws_s3_bucket_website_configuration.app_client_bucket_website.website_domain
  description = "aws_s3_bucket_website_configuration.app_client_bucket_website.website_domain"
}
output "s3_sync_command" {
  value       = "aws s3 sync ../client/dist s3://${aws_s3_bucket.app_client_bucket.bucket}/ --delete"
  description = "CMD to sync SPA manually"
}

