# Library Management System

## Overview
This project is a microservice for managing books and authors in a library. It uses Spring Boot for the microservice, PostgreSQL for the database, and Kafka for messaging.

## Prerequisites
- Docker
- Docker Compose
- Java 21
- Maven

## Setup

### Database
1. PostgreSQL is included as part of the Docker Compose setup. It will be automatically set up and initialized when you run the Docker Compose command.

### Kafka
1. Kafka and Zookeeper are included as part of the Docker Compose setup. They will be automatically set up when you run the Docker Compose command.

### Running the Application
1. Clone the repository:
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. Build the application using Maven:
    ```sh
    mvn clean package -DskipTests
    ```

3. Run the application using Docker Compose:
    ```sh
    docker-compose up --build
    ```

4. Verify that all services are running by checking the Docker containers:
    ```sh
    docker ps
    ```
   You should see containers for the application, PostgreSQL, Kafka, and Zookeeper.


### Verifying Kafka Topic

1. **Find Kafka Broker IP Address**:
   Use the following command to find the IP address of your Kafka broker container:
   ```sh
   docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <your-kafka-container-name>
   

2. **List Topics**:
   Use the following command to list all Kafka topics and ensure `library-topic` is created:
   ```sh
   docker exec -it <your-kafka-container-name> kafka-topics --list --bootstrap-server <IP_ADDRESS>:9092

3. **If not use below Command to create**:

   docker exec -it <your-kafka-container-name> kafka-topics --create --bootstrap-server <IP_ADDRESS>:9092 --replication-factor 3 --partitions 1 --topic library-topic



## API Endpoints

### Books
- `GET /api/books`: Retrieve a list of all books.
- `GET /api/books/{id}`: Retrieve a book by its ID.
- `POST /api/books`: Add a new book.
- `PUT /api/books/{id}`: Update a book by its ID.
- `DELETE /api/books/{id}`: Delete a book by its ID.

## Postman Collection
A Postman collection is provided for testing the API. Take the collection into Postman to test the endpoints from below path:
Assignment\Assignment_collection.postman_collection.json


## Swagger
The application uses Swagger for API documentation. Once the application is running, you can access the Swagger UI at:
http://localhost:8080/swagger-ui.html
