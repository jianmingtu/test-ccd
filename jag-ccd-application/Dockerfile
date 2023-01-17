FROM eclipse-temurin:11-jre-alpine

COPY ./target/ccd-application-1.1.jar ccd-application-1.1.jar

ENTRYPOINT ["java","-Xmx1g", "-jar","/ccd-application-1.1.jar"]
