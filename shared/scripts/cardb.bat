@ECHO OFF
cd ./carDB
docker buildx build -t cars:dev --build-context slice=../shared/slice .
docker-compose up cars
