# Spring Kafka Avro Producer-Consumer

This Spring Boot project, `spring-kafka-avro-producer-consumer`, demonstrates the integration of Apache Kafka with Avro serialization using Confluent Schema Registry. The application implements both Kafka producer and consumer functionality, exposing a RESTful HTTP POST endpoint that accepts form parameters to publish user information to a Kafka topic. The consumer automatically processes messages from the same topic and logs them. Additionally, it provides comprehensive Actuator endpoints for monitoring and management.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Building the Project](#building-the-project)
  - [Setting up Kafka Locally](#setting-up-kafka-locally)
- [Configuration](#configuration)
  - [Local Development](#local-development)
  - [Production (Confluent Cloud)](#production-confluent-cloud)
- [Usage](#usage)
  - [RESTful Endpoint](#restful-endpoint)
  - [Actuator Endpoints](#actuator-endpoints)
- [Application Components](#application-components)
- [Avro Schema](#avro-schema)
- [Build Configuration](#build-configuration)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Ensure you have the following installed on your development machine:

- [Java Development Kit (JDK) 17](https://openjdk.java.net/)
- [Apache Maven](https://maven.apache.org/) (primary build tool)
- [Gradle](https://gradle.org/) (alternative build tool - both configurations available)

### Building the Project

The project is built with Spring Boot version 3.2.0 and supports both Maven and Gradle. Clone the repository:

```bash
git clone https://github.com/uknadkarni/spring-kafka-avro-producer-consumer.git
cd spring-kafka-avro-producer-consumer
```

**Build with Maven:**
```bash
./mvnw clean compile
```

**Build with Gradle:**
```bash
./gradlew build
```

### Setting up Kafka Locally
To set up Kafka locally, follow these steps:
1. Extract the latest version of Confluent Platform
2. Start Zookeeper: `./bin/zookeeper-server-start ./etc/kafka/zookeeper.properties` 
3. Start Kafka broker: `./bin/kafka-server-start ./etc/kafka/server.properties`
4. Start Schema Registry: `./bin/schema-registry-start etc/schema-registry/schema-registry.properties` 
4. Control Center: Optionally, for monitoring, download and start Confluent Control Center: `./bin/control-center-start ./etc/confluent-control-center/control-center.properties` 

### Configuration
The application uses different configuration files based on the Kafka environment.

#### Local Development
For local development with Confluent Platform running on localhost, the application uses the `application-dev.properties` file (activated by default via `spring.profiles.active=dev`). Key configurations include:

- **Kafka Broker**: `localhost:9092`
- **Schema Registry**: `http://localhost:8081`
- **Topic**: `users` (1 partition, replication factor 1)
- **Consumer Group**: `my-microservice`
- **Server Port**: `8080`
- **Monitoring**: Confluent monitoring interceptors enabled for both producer and consumer

#### Production (Confluent Cloud)
For production deployment on Confluent Cloud, create an `application-prod.properties` file with configurations tailored for the Confluent Cloud environment.

### Usage
#### RESTful Endpoint
The application exposes a RESTful HTTP POST endpoint at `/user/publish` that accepts **form parameters** (not JSON). Send data using `application/x-www-form-urlencoded` format:

**Using curl:**
```bash
curl -X POST http://localhost:8080/user/publish \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "name=John Doe&age=25"
```

**Using HTML form:**
```html
<form action="/user/publish" method="post">
  <input type="text" name="name" value="John Doe" />
  <input type="number" name="age" value="25" />
  <input type="submit" value="Publish" />
</form>
```

**Parameters:**
- `name` (String, required): User's name
- `age` (Integer, required): User's age (must be non-negative)

**Response:**
- Success: `200 OK` with message "User [User details] published successfully"
- Error: `400 Bad Request` with message "Age must be a non-negative value" (if age < 0)

This endpoint creates a User object from the form parameters, publishes it to the configured Kafka topic using Avro serialization, and returns a confirmation message.

#### Actuator Endpoints

The application exposes comprehensive Actuator endpoints for monitoring and management (all endpoints are enabled via `management.endpoints.web.exposure.include=*`):

- **/actuator/health**: Application health information with detailed status
- **/actuator/info**: Custom application information
- **/actuator/metrics**: Application metrics and performance data
- **/actuator/env**: Environment properties and configuration
- **/actuator/prometheus**: Prometheus metrics endpoint (enabled)

These endpoints can be accessed at `http://localhost:8080/actuator/{endpoint}` for comprehensive application monitoring.

## Application Components

### Producer (`Producer.java`)
- **Purpose**: Sends User messages to Kafka topic using Avro serialization
- **Key Method**: `send(User user)` - publishes user to the configured topic with user's name as the message key
- **Logging**: Logs all sent messages at WARN level

### Consumer (`Consumer.java`)
- **Purpose**: Automatically consumes User messages from Kafka topic
- **Annotation**: `@KafkaListener` for automatic message consumption
- **Group ID**: Uses `my-microservice` consumer group
- **Logging**: Logs all consumed messages at WARN level

### REST Controller (`KafkaController.java`)
- **Purpose**: Exposes HTTP endpoint for publishing user data
- **Endpoint**: `POST /user/publish`
- **Validation**: Ensures age is non-negative
- **Integration**: Uses Producer component to send messages to Kafka

### Main Application (`DemoApplication.java`)
- **Purpose**: Spring Boot application entry point
- **Topic Management**: Automatically creates Kafka topic with configurable partitions and replication factor
- **Configuration**: Reads topic settings from application properties

## Avro Schema

The Avro schema for the User object is defined in `src/main/avro/user.avsc`:

```json
{
  "namespace": "io.confluent.developer",
  "type": "record",
  "name": "User",
  "fields": [
    {
      "name": "name",
      "type": "string",
      "avro.java.string": "String"
    },
    {
      "name": "age",
      "type": "int"
    }
  ]
}
```

This schema generates a Java class `io.confluent.developer.User` during the Maven build process using the Avro Maven plugin. The schema ensures type safety and enables schema evolution capabilities.

## Build Configuration

### Maven Configuration (`pom.xml`)
The project uses Maven as the primary build tool with the following key features:

- **Spring Boot Parent**: Version 3.2.0
- **Java Version**: 17
- **Key Dependencies**:
  - Spring Boot Starter Web & Actuator
  - Spring Kafka
  - Confluent Kafka Avro Serializer (7.5.1)
  - Apache Avro (1.11.3)
  - Confluent Schema Registry Client (7.5.1)
  - Confluent Monitoring Interceptors

- **Build Plugins**:
  - **Avro Maven Plugin**: Generates Java classes from Avro schemas
  - **Build Helper Plugin**: Adds generated sources to compilation path
  - **Spring Boot Maven Plugin**: Creates executable JAR

- **Repositories**: Includes Confluent Maven repository for Confluent-specific dependencies

### Gradle Configuration (`build.gradle`)
Alternative Gradle build configuration is available with Spring Boot 2.5.4 and Java 11 compatibility.

### Running the Application

**With Maven:**
```bash
./mvnw spring-boot:run
```

**With Gradle:**
```bash
./gradlew bootRun
```

**As JAR:**
```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Contributing
We welcome contributions to this project! To contribute, follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push your changes to your fork.
5. Create a pull request to the main repository.

Please follow the contribution guidelines for more information on coding standards and best practices.

