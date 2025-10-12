FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/ecommerce-api-0.0.1-SNAPSHOT.jar ecommerce-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ecommerce-api.jar"]