# syntax=docker/dockerfile:1.4
FROM node:18.9-slim AS environment
RUN npm install -g truffle

FROM environment AS build
WORKDIR /contracts
ENTRYPOINT truffle compile && truffle migrate

VOLUME [ "/contracts" ]
