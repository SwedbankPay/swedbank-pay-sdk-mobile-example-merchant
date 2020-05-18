FROM openjdk:8-jdk-alpine

# Create app directory
WORKDIR /usr/src/app

# Copy our input jar file to fixed app.jar
COPY build/libs/merchant-1.0.1-SNAPSHOT.jar app.jar

# Expose a TCP port for incoming traffic
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]