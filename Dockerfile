FROM gradle:8.14.3-jdk21 as builder
WORKDIR /home/gradle/feedApp
COPY . .
RUN ./gradlew bootJar
RUN mv backend/build/libs/backend-0.0.1-SNAPSHOT.jar FeedApp.jar

FROM eclipse-temurin:21-alpine
RUN addgroup -g 1000 app
RUN adduser -G app -D -u 1000 -h /app FeedAppGuest


USER FeedAppGuest
WORKDIR /app


COPY --from=builder --chown=1000:1000 /home/gradle/feedApp/FeedApp.jar .

EXPOSE 8080
CMD ["java", "-jar", "FeedApp.jar"]