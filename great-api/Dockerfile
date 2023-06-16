# First stage: complete build environment
FROM maven:3.8.6-openjdk-18-slim AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean -DskipTests package

# Second stage: minimal runtime environment
FROM openjdk:20-slim

EXPOSE 8090

COPY  --from=builder ./target/*.jar /app/application.jar

ENTRYPOINT java $JAVA_OPTS -jar /app/application.jar
