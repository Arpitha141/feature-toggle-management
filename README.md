# Feature Toggle Management System

A robust Spring Boot REST API designed to manage feature toggles used in real-world deployment pipelines. This application allows teams to seamlessly enable or disable features across different environments (DEV, QA, PROD) and application versions.

## 🚀 Features

* **Create Feature Toggles:** Define new features by name, environment, and application version.
* **Status Checking:** Quickly verify if a feature is enabled for a specific environment and version.
* **Idempotent Toggling:** Safely enable or disable existing features.
* **Strict Validation:** Rejects invalid feature names (no uppercase, spaces, or leading/trailing hyphens) and malformed requests.
* **Concurrency Protection:** Implements JPA Optimistic Locking to prevent simultaneous conflicting updates by different administrators.
* **Security:** Core API endpoints are secured with HTTP Basic Authentication.
* **Monitoring:** Exposes application health and metadata via Spring Boot Actuator.

## 🛠️ Technology Stack

* **Java:** 21
* **Framework:** Spring Boot 4.x
* **Data Access:** Spring Data JPA
* **Database:** H2 In-Memory Database
* **Security:** Spring Security (Basic Auth)
* **API Documentation:** Springdoc OpenAPI (Swagger UI)
* **Monitoring:** Spring Boot Actuator

## 📋 Prerequisites

* **Java 21** or higher installed on your machine.
* **Maven** (Optional, as the project includes a Maven wrapper).

## 🚦 Running the Application

1. **Clone the repository:**
   ```bash
   git clone <your-repository-url>
   cd feature-toggle-system

2. **Run the application using Maven Wrapper:**

Bash
./mvnw spring-boot:run
(On Windows, use mvnw.cmd spring-boot:run)

3. The application will start on port 8080.

## 🔐 Credentials (Basic Auth)
To access the secured /features endpoints or authorize via Swagger UI, use the following credentials:

* **Username:** admin
* **Password:** admin123

## 🔗 Important URLs
Once the application is running, you can access the following interfaces:

* **Swagger UI (API Documentation):** http://localhost:8080/swagger-ui.html

(Publicly accessible. Click the green "Authorize" button to enter credentials before testing the POST/PUT endpoints.)

* **H2 Database Console:** http://localhost:8080/h2-console

(Publicly accessible)

* **JDBC URL:** jdbc:h2:mem:featuredb
* **Username:** sa
* **Password:** password

* **Actuator Health Endpoint:** http://localhost:8080/actuator/health

* **Actuator Info Endpoint:** http://localhost:8080/actuator/info

## 📖 API Endpoints Overview
1. **Create Feature Toggle**
* **URL:** POST /features
* **Auth Required:** Yes
* **Payload:**

JSON
{
  "featureName": "new-checkout-flow",
  "environment": "DEV",
  "appVersion": "v1.0",
  "enabled": true
}

2. **Check Feature Status**
* **URL:** GET /features/check
* **Auth Required:** Yes
* **Parameters:** featureName (String), environment (Enum), appVersion (String)

3. **Toggle Feature**
* **URL:** PUT /features/{id}/toggle
* **Auth Required:** Yes
* **Payload:**

JSON
{
  "enabled": false
}

## 🏗️ Exception Handling
The API implements centralized error handling using `@ControllerAdvice` to return standardized JSON responses for:

* **Validation Errors:** Returns a detailed map of all failing fields.
* **Malformed JSON:** Prevents default stack traces when JSON cannot be parsed.
* **Entity Not Found:** Handled gracefully when requesting non-existent features.
* **Data Integrity / Conflicts:** Protects against duplicate feature creations and optimistic locking concurrency failures.