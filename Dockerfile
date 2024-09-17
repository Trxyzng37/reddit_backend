#FROM openjdk:17-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} TRXYZNG-1.1.jar
#EXPOSE 8080
#EXPOSE 1433
#ENTRYPOINT ["java","-jar","/TRXYZNG-1.1.jar"]