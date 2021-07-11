FROM gradle:6-jdk11 AS build-env
RUN mkdir /app
RUN mkdir /app/src

ADD gradle.properties /app
ADD settings.gradle.kts /app
ADD build.gradle.kts /app
ADD src /app/src/

WORKDIR /app
RUN gradle build --no-daemon

FROM gcr.io/distroless/java:11
COPY --from=build-env /app/build/libs/deep-thought-*.jar /app/deep-thought.jar
WORKDIR /app
CMD ["deep-thought.jar"]

