cd ./tarrif
docker buildx build -t tarrif:dev --build-context slice=../shared/slice .
docker-compose up -d tarrif
cd ..
