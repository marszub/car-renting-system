@ECHO OFF
docker network create --attachable --driver=bridge --subnet=172.16.7.0/24 --ip-range=172.16.7.0/24 --gateway=172.16.7.254 car-rent

cd ./auth
docker buildx build -t auth:dev --build-context slice=../shared/slice .
docker-compose up auth
