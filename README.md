# Spring Kafka Avro Producer-Consumer

This Spring Boot project, `spring-kafka-avro-producer-consumer`, is designed to demonstrate the integration of Kafka with Avro serialization. The application serves as a Kafka Producer and Consumer, exposing a RESTful HTTP POST endpoint to publish user information to a Kafka topic. Additionally, it provides Actuator endpoints for monitoring and management.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Building the Project](#building-the-project)
- [Configuration](#configuration)
  - [Local Development](#local-development)
  - [Production (Confluent Cloud)](#production-confluent-cloud)
- [Usage](#usage)
  - [RESTful Endpoint](#restful-endpoint)
  - [Actuator Endpoints](#actuator-endpoints)
- [Avro Schema](#avro-schema)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Ensure you have the following installed on your development machine:

- [Java Development Kit (JDK) 17](https://openjdk.java.net/)
- [Apache Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/) (for Kafka setup in local development)

### Building the Project

The project was built with Spring Boot version 3.2.0. Clone the repository:

```bash
git clone https://github.com/uknadkarni/spring-kafka-avro-producer-consumer.git
cd spring-kafka-avro-producer-consumer

### Configuration
The application uses different configuration files based on the Kafka environment.

#### Local Development
For local development with Confluent Platform running on localhost, use the application-dev.properties file. This file includes settings relevant to the local Kafka setup.

#### Production (Confluent Cloud)
For production deployment on Confluent Cloud, use the application-prod.properties file. This file includes configurations tailored for the Confluent Cloud environment.

### Usage
#### RESTful Endpoint
The application exposes a RESTful HTTP POST endpoint at /users/publish to post a user's name and age. The payload should be in JSON format:

{
  "name": "John Doe",
  "age": 25
}

This endpoint publishes the user information to the configured Kafka topic.

#### Actuator Endpoints

The Actuator endpoints provide various monitoring and management features:

**/actuator/health:** Application health information
**/actuator/info:** Custom application information
**/actuator/metrics:** Metrics information
**/actuator/env:** Environment properties

These endpoints can be accessed for monitoring and managing the application.

### Avro Schema

The Avro schema for the User object is defined in the file src/main/avro/user.avsc. The schema specifies the structure of the user data being sent to Kafka. Ensure that any data posted to the /users/publish endpoint adheres to this Avro schema.

### Contributing
We welcome contributions to this project! To contribute, follow these steps:

Fork the repository.
Create a new branch for your feature or bug fix.
Make your changes and commit them.
Push your changes to your fork.
Create a pull request to the main repository.
Please follow the contribution guidelines for more information on coding standards and best practices.

