FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src
FROM eclipse-temurin:17-jdk

RUN chmod +x gradlew
RUN ./gradlew clean bootJar

FROM eclipse-temurin:17-jre
COPY --from=build /workspace/build/libs/*.jar /app.jar
COPY build/libs/*SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
