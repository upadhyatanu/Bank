FROM openjdk:17-jdk-slim

LABEL "org.opencontainers.image.authors"="tanushree"

#copy application
COPY target/Account-0.0.1-SNAPSHOT.jar Account-0.0.1-SNAPSHOT.jar

#execute application
ENTRYPOINT ["java", "-jar", "Account-0.0.1-SNAPSHOT.jar"]