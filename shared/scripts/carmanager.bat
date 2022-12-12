cd ./carManager
docker buildx build -t carmanager:dev --build-context slice=../shared/slice .
docker-compose up -d carManager
cd ..
