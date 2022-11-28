# syntax=docker/dockerfile:1.4
FROM gradle:jdk18-focal AS environment
RUN apt-get update
RUN apt-get -y install zeroc-ice-compilers


FROM environment AS build
WORKDIR /home/gradle/rental
COPY --chown=gradle:gradle ./build.gradle ./
COPY --chown=gradle:gradle ./gradlew ./
COPY --chown=gradle:gradle ./settings.gradle ./
COPY --chown=gradle:gradle ./src/main ./src/main
COPY --chown=gradle:gradle ./gradle ./gradle
COPY --from=slice --chown=gradle::gradle . /home/gradle/shared/slice

# load application.properties from external file
RUN echo "spring.config.import=file:/config/rental.properties" \
    > ./src/main/resources/application.properties
# build app
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:18-jdk-focal AS publish
RUN mkdir /app
COPY --from=build /home/gradle/rental/build/libs/*.jar /app/app.jar
WORKDIR /app

EXPOSE 5020
EXPOSE 5022
VOLUME /config

ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]
