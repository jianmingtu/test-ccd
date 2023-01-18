FROM eclipse-temurin:11-jre-alpine

COPY ./target/ccd-application.jar ccd-application.jar

ENTRYPOINT ["java","-Xmx1g", "-jar","/ccd-application.jar"]
