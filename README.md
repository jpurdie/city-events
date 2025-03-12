# City Events Application

## Overview
This project is a **serverless event management platform** that allows city citizens to view, register, and manage attendance for local events.  
It is built with **AWS Lambda, DynamoDB, S3, Vue 3, and Terraform** for infrastructure as code.  

## Architecture
```
/infra -> Terraform configuration for infrastructure (DynamoDB, Lambda, S3, IAM Policies) 

/client -> Vue 3 Single Page Application (SPA) for event management 

/api -> AWS Lambda (Java 21, Maven) for event-related API endpoints 

/makefile -> Automates the build process
```

## Technology Stack
- **Frontend**: Vue 3, Bootstrap 5 (hosted on S3)  
- **Backend**: AWS Lambda (Java 21, Maven), DynamoDB  
- **Infrastructure**: Terraform (managing AWS resources)  
- **Authentication**: Mocked (any email & password will work)  

## Features
✔️ **Event Management** → View and register for local events  
✔️ **Serverless Architecture** → Uses AWS Lambda Function URLs  
✔️ **Infrastructure as Code** → Terraform manages AWS resources  
✔️ **Stateless API** → DynamoDB for storage  
✔️ **Single Page App (SPA)** → Hosted in an S3 bucket  
✔️ **Makefile for Build Automation** → One-command build  

## Deployment

### Prerequisites
- **AWS CLI & Terraform installed**  
- **Make installed** (for executing the `Makefile`)  
- **Node.js & npm installed** for the Vue app  
- **Java 21 & Maven installed** for the API  

### Build Everything (One-Command)
Use the **Makefile** to build the API and frontend in one step:  

Run the following from the **project root**:  
```bash
make 
```
This will:

✔️ Build and package the API (Maven)

✔️ Build the SPA (Vue 3)


### Deploy Infrastructure

After building, deploy the infrastructure using Terraform:
```bash
cd infra
terraform init
terraform apply
```

## TODOs

 🔧 CI/CD with GitHub Workflows → Automate deployments
 
📱 Mobile Responsiveness → Improve UI on small screens

♿ WCAG/Accessibility Features → Ensure accessibility compliance

📄 OpenAPI Schema → Generate API documentation

📊 Error Logging & Monitoring → Add AWS CloudWatch logs

🧪 Unit & Integration Tests → Improve API & client testing

🔍 Static Code Analysis -> Improve code quality 

🗺️ i18n & l10n -> Support for internationalization and localization 

