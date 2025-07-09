FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/usergym-0.0.1-SNAPSHOT.jar app.jar

COPY src/main/resources/keys/ ./keys/

EXPOSE 8082

# Comando para correr la app
ENTRYPOINT ["java","-jar","app.jar"]
