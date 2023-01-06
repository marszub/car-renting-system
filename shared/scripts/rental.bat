cd ./rental
docker buildx build -t rental:dev --build-context slice=../shared/slice .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up -d rental
:SKIPRUN
cd ..
