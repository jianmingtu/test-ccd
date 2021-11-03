FROM openjdk:11-jre-slim

COPY ./pcss-civil-application/target/ccd-application.jar ccd-application.jar

ENTRYPOINT ["java","-jar","/ccd-application.jar"]
