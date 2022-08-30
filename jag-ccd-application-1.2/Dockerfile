FROM openjdk:11-jre-slim

COPY ./target/ccd-application-1.2.jar ccd-application-1.2.jar

ENTRYPOINT ["java","-Xmx1g", "-jar","/ccd-application-1.2.jar"]
