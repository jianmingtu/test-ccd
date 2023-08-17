FROM eclipse-temurin:11-jre-alpine

COPY ./target/ccd-application.jar ccd-application.jar

ENTRYPOINT ["java","-Xmx2g", "-jar","/ccd-application.jar"]
