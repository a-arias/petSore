# Swagger Pet store APIs project

## Overview

This project uses REST-assured to test  Swagger Pet store APIs project. REST-assured is a Java library that simplifies the process of testing REST services. It provides a domain-specific language (DSL) for writing tests that are readable and maintainable.

## Prerequisites

- **Java 8 or higher**: Ensure that you have Java installed on your system.
- **Maven**: The project uses Maven for dependency management. Install Maven if you haven't already.
- Follow this link for getting information about the Swagger Pet store project.
- Setup the API Pet store and run it locally.
- You should be able to see the Swagger service running with all the api endpoints examples on http://localhost:8080

## Getting Started

### Clone the Repository

First, clone this repository to your local machine:

```bash
git clone git@github.com:a-arias/petSore.git
cd pet
```

### Set Up the Project
Import the Project: Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
Build the Project: Use Maven to install the required dependencies and build the project:

```bash
mvn clean install
```

### Configuration

Before running the tests, configure the necessary settings:

API Base URL: Update the base URL of the API in the configuration file or environment variables.
Running the Tests

To run the tests, use the following Maven command:
```bash
mvn test
```
