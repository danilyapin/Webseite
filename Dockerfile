FROM openjdk:17

EXPOSE 8081

ADD Backend/webseite/target/MietManager.jar MietManager.jar

ENTRYPOINT ["java", "-jar", "MietManager.jar"]