FROM openjdk:11-jre-slim

COPY ./jag-ccd-application/target/ccd-application.jar ccd-application.jar

ENTRYPOINT ["java","-jar","/ccd-application.jar"]
