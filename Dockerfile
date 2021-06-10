FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

EXPOSE 8044

ENTRYPOINT ["java", "-jar", "/application.jar"]