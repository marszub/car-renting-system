@ECHO OFF
docker network create --attachable --driver=bridge --subnet=172.16.7.0/24 --ip-range=172.16.7.0/24 --gateway=172.16.7.254 car-rent

cd ./carDB
docker buildx build -t cars:dev --build-context slice=../shared/slice .
docker-compose up cars
