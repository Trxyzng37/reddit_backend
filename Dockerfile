FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Backend-1.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/Backend-1.1.jar", "--server.address=0.0.0.0", "--server.port=8080"]