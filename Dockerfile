FROM openjdk:17-jdk-slim

LABEL maintainer="eazybytes.com"

COPY target/Travel-0.0.1-SNAPSHOT.jar Travel-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "Travel-0.0.1-SNAPSHOT.jar"]
