cd ./rental
docker buildx build -t rental:dev --build-context slice=../shared/slice .
docker-compose up -d rental
cd ..
