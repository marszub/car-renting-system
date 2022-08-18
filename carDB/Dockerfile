# syntax=docker/dockerfile:1.4
FROM gradle:jdk18-focal AS build
COPY --chown=gradle:gradle . /home/gradle/carDB
WORKDIR /home/gradle/carDB

# load application.properties from external file
RUN echo "spring.config.import=file:/config/cardb.properties" \
    > ./src/main/resources/application.properties
# build app
RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:18-jdk-focal AS publish
RUN mkdir /app
COPY --from=build /home/gradle/carDB/build/libs/*.jar /app/app.jar
WORKDIR /app

EXPOSE 5030
VOLUME /config

ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
