# First stage: complete build environment
FROM maven:3.8.6-openjdk-18-slim AS builder

# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

RUN mvn dependency:go-offline

# package jar
RUN mvn clean -DskipTests package

# Second stage: minimal runtime environment
FROM openjdk:20-slim

EXPOSE 8080
EXPOSE 9011

COPY  --from=builder ./target/*.jar /app/application.jar

CMD java $JAVA_OPTS  \
-Dcom.sun.management.jmxremote=true \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Djava.rmi.server.hostname=127.0.0.1 \
-Dcom.sun.management.jmxremote.host=0.0.0.0 \
-Dcom.sun.management.jmxremote.port=9011 \
-Dcom.sun.management.jmxremote.rmi.port=9011 \
-Djava.net.preferIPv4Stack=true \
-jar /app/application.jar
