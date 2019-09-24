FROM openjdk:8-jdk-alpine

# Create app directory
WORKDIR /usr/src/app

# Copy our input jar file to fixed app.jar
COPY build/libs/merchant-sample-java-1.0.0.jar app.jar

# Expose a TCP port for incoming traffic
EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]