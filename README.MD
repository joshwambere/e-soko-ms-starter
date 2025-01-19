# Spring Cloud Microservices Architecture

## Overview

This architecture consists of the following core components:
- **API Gateway**: Acts as a reverse proxy, routing requests to different microservices based on the URL path. The gateway performs authentication filtering before forwarding requests to respective services.
- **User Service**: A simple service that provides functionality to manage users, including saving user data.
- **IAM Service**: A service that handles authentication and authorization.
- **Discovery Service (Eureka Server)**: A service for dynamic registration and discovery of microservices.

This setup uses **Spring Cloud Gateway** for routing requests, **Spring Boot** for building the microservices, and **Spring Cloud Netflix Eureka** for service discovery.

## Architecture Components

### 1. API Gateway (Spring Cloud Gateway)

The **API Gateway** is the entry point for all incoming requests. It routes requests to the appropriate microservices based on the path. It also applies an **Authentication Filter** to check if the request has a valid Authorization header before forwarding the request to the target service.

The routing logic in the API Gateway is defined in the `GatewayConfig` class. The routes are configured as follows:
- **`/users/**`** is routed to the `user-service`.
  - To test the Routing send a POST request to `http://localhost:8080/users` with a JSON body like `{"password": "John123", "email": "johndoe@awesomity.rw"}`"
- **`/iam/**`** is routed to the `iam-service`.

The **Authentication Filter** ensures that any requests to secured paths are validated for the presence of a valid `Authorization` header (using a Bearer token).

### 2. User Service

The **User Service** is a simple service that provides functionality for managing user data. For simplicity, this service includes only a `POST /users` endpoint, which allows creating a new user by saving their data.

The service generates a unique `ID` for the user using `UUID` and returns the created `UserDto`.

### 3. IAM Service

The **IAM Service** (not fully detailed here) handles authentication-related functionality. It is routed through the API Gateway under the path `/iam/**`.

### 4. Discovery Service (Eureka Server)

The **Discovery Service** is a Spring Cloud Netflix Eureka server that allows microservices to register themselves and discover other microservices dynamically. When a service starts, it registers itself with Eureka, making it available to other services (like the Gateway) to discover and communicate with.

### 5. Test Endpoint

To verify the setup, we have a test endpoint that simulates the creation of a user. This endpoint can be accessed via the API Gateway, which routes requests to the `user-service`.

## Routing Configuration

The routes are configured in the `GatewayConfig` class with the following details:

```kotlin
@Bean
fun routes(builder: RouteLocatorBuilder): RouteLocator {
    return builder.routes()
        .route("user-service") { r: PredicateSpec ->
            r.path("/users/**")
                .filters { f: GatewayFilterSpec -> f.filter(filter) }
                .uri("lb://user-service")
        }
        .route("iam-service") { r: PredicateSpec ->
            r.path("/iam/**")
                .filters { f: GatewayFilterSpec -> f.filter(filter) }
                .uri("lb://iam-service")
        }
        .build()
}
