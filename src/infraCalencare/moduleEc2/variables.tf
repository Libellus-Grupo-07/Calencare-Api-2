variable "az" {
  description = "Availability Zone"
  type        = string
  default     = "us-east-1a"
}

variable "key_pair_name" {
  description = "Key Pair Name"
  type        = string
  default     = "teste"
}

variable "ami" {
  description = "AMI ID"
  type        = string
  default     = "ami-005fc0f236362e99f"  # O ID da AMI
}


variable "inst_type" {
  description = "Instance Type"
  type        = string
  default     = "t2.medium"
}

variable "subnet_id" {
  description = "Subnet ID"
  type        = string
  default     = "subnet-024d426e8b3131b47"
}

variable "sg_id" {
  description = "Security Group ID"
  type        = string
  default     = "sg-01a495c5d1da3c727"
}

variable "dockerhub_username" {
  description = "Docker Hub username"
  type        = string
}

variable "snapshot_id" {
  default = "snap-03c1449533e16cc6b"
  description = "Snapshot ID Backend"
  type        = string
}