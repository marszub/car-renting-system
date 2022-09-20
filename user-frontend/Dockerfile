# syntax=docker/dockerfile:1.4
FROM node:18.9-slim AS environment
WORKDIR /app
COPY . /app

RUN npm install
ENTRYPOINT [ "/app/docker-start.sh" ]

VOLUME [ "/config" ]
