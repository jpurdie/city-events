resource "aws_dynamodb_table" "events_registration_data_table" {
  name                        = "${var.app_name}-events-registration"
  billing_mode                = "PAY_PER_REQUEST"
  deletion_protection_enabled = false

  hash_key  = "userId"
  range_key = "eventId"

  attribute {
    name = "userId"
    type = "S"
  }

  attribute {
    name = "eventId"
    type = "S"
  }

  tags = merge(
    var.tags,
    {
      Name = "${var.app_name}"
    }
  )
}
