FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

RUN chmod +x gradlew
RUN ./gradlew clean bootJar -x test --no-daemon --max-workers=1

FROM eclipse-temurin:17-jre
COPY --from=build /workspace/build/libs/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
