## Overview

The Palindrome Project is a Spring Boot application that checks if a given text is a palindrome. It features a RESTful API for easy interaction, uses caching for efficiency, and stores data in a file.

### Build Tools

- **Maven**: Used for building and managing dependencies. Maven simplifies configuration and ensures consistent builds.

### Architecture

- **Spring Boot**: Provides an easy setup for production-ready applications with minimal configuration, including embedded servers and auto-configuration.

- **Reactor**: Enables non-blocking, asynchronous processing for better scalability and efficiency.

- **File-Based Persistence**: Keeps data storage simple and lightweight, avoiding the need for a full database setup.

- **Caching**: Improves performance by storing and reusing results of previous checks.

### REST API Rationale

- **RESTful Design**: Chosen for its simplicity and scalability. REST is stateless, making it easy to handle various client interactions efficiently.

- **Endpoint Design**: The `/api/palindrome` endpoint uses `POST` to handle text checks, fitting the need for operations that process data and store results.

- **Reactive Programming**: Utilizes Reactor’s `Mono` to handle requests asynchronously, improving scalability and responsiveness.

- **Error Handling**: Consistent global error handling ensures reliable and informative responses to clients.

This setup provides a straightforward, efficient, and scalable solution for checking palindromes.
