FROM openjdk:8-jdk-alpine as build 
ARG JAR_FILE=target/*.jar 
COPY Project08-Service-Location-1.0.0.jar Service-Location-1.0.0.jar 
ENTRYPOINT ["java","-jar","/Service-Location-1.0.0.jar"]
