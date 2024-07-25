# Palindrome Checker Project

## Overview

The Palindrome Checker Project is a web application designed to check if a given text is a palindrome. It utilizes Spring Boot for its backend services and Reactor for reactive programming. The project is structured to use an in-memory caching system, which can be easily replaced with other caching implementations.

### Build Tools and Architecture

- **Build Tools**: The project uses Maven for dependency management and build automation. Maven helps manage project dependencies, build processes, and configuration.
  
- **Architecture**:
  - **Spring Boot**: Provides the foundation for the application with embedded servers and configuration management.
  - **Reactor**: Utilized for reactive programming, enabling non-blocking operations and asynchronous data processing.
  - **In-Memory Cache**: An in-memory ICache implementation using `ConcurrentHashMap`, allowing thread-safe operations. This ICache can be replaced with other implementations like Redis if needed.

### REST API Rationale

The REST API is designed to provide a simple and efficient interface for checking if a text is a palindrome. The API uses a POST method to handle requests, allowing clients to send both the username and the text for checking. This approach:
- **Encapsulates Business Logic**: The palindrome checking logic is encapsulated in a service layer, making it reusable and testable.
- **Supports Asynchronous Processing**: By using `Mono` from Reactor, the API can handle asynchronous and non-blocking operations efficiently.
- **Provides Caching**: Caching mechanisms improve performance by storing previously checked texts, reducing redundant computations.

## Getting Started

### Prerequisites

- Java 17 or later
- Maven

### Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/shaneharrigan/pali-project.git
   cd pali-project
   ```
2. **Build the project:**
   ```bash
   mvn clean install
   ```
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
## API Endpoints

- Check Palindrome
- URL: /api/palindrome
- Method: POST
- Parameters:
- username (String) - The username associated with the request.
- text (String) - The text to check.
- Response: Boolean - Returns true if the text is a palindrome, false otherwise.

  ```bash
  curl -X POST "http://localhost:8080/api/palindrome" -d "username=user&text=madam"
  ```

## Caching

The project includes an InMemoryCache implementation. The ICache is used to store and retrieve previously checked texts to improve performance. The caching mechanism can be replaced with other implementations by modifying the Cache interface and its implementations.
