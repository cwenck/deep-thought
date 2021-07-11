FROM openjdk:11-jdk-slim AS build-env
ADD . /app
WORKDIR /app
RUN chmod +x ./gradlew && ./gradlew build

FROM gcr.io/distroless/java:11
COPY --from=build-env /app/build/libs/deep-thought-*.jar /app/deep-thought.jar
WORKDIR /app
CMD ["deep-thought.jar"]

