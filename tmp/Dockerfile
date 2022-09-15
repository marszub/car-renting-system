# syntax=docker/dockerfile:1.4
FROM ubuntu:focal
COPY ./scan.sh .
ENTRYPOINT [ "./scan.sh", "host.docker.internal" ]
