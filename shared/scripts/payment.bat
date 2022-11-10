cd ./payment
docker buildx build -t payment:dev --build-context slice=../shared/slice .
docker-compose up -d payment
cd ..
