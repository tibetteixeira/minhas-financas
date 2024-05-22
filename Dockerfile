FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} minhasfinancas.jar
ENTRYPOINT ["java","-jar","minhasfinancas.jar"]