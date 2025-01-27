# Stage 1: Build Stage using Amazon Corretto 21 and Maven (optimized)
FROM amazoncorretto:21-alpine3.18-jdk AS build

# Install Maven manually in the build stage
RUN apk update && apk add maven && rm -rf /var/cache/apk/*

# Set working directory
WORKDIR /app

# Copy the pom.xml to leverage Docker cache
COPY pom.xml ./

# Download dependencies (to take advantage of Docker cache)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src /app/src

# Build the application (skip tests)
RUN mvn clean package -DskipTests

# Stage 2: Runtime Stage using Amazon Corretto 21
FROM amazoncorretto:21-alpine3.18-jdk

# Install Netcat (openbsd version) for health checks in the runtime stage
RUN apk update && apk add netcat-openbsd && rm -rf /var/cache/apk/*

# Set working directory
WORKDIR /app

# Set Java environment variables for Amazon Corretto 21
ENV JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto
ENV PATH=$JAVA_HOME/bin:$PATH

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/Assignment-0.0.1-SNAPSHOT.jar app.jar

# Run the Spring Boot application, waiting for PostgreSQL, Kafka, and ZooKeeper to be ready
ENTRYPOINT ["sh", "-c", "until nc -z postgres 5432; do echo 'Waiting for postgres...'; sleep 2; done; \
  until nc -z kafka-1 9092; do echo 'Waiting for kafka-1...'; sleep 2; done; \
  until nc -z kafka-2 9094; do echo 'Waiting for kafka-2...'; sleep 2; done; \
  until nc -z kafka-3 9096; do echo 'Waiting for kafka-3...'; sleep 2; done; \
  until nc -z zookeeper 2181; do echo 'Waiting for zookeeper...'; sleep 2; done; \
  java -jar /app/app.jar"]
