@ECHO OFF
cd ./auth
docker buildx build -t auth:dev --build-context slice=../shared/slice .
docker-compose up auth
