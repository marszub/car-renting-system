cd ./carDB
docker buildx build -t cars:dev --build-context slice=../shared/slice .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up -d cars
:SKIPRUN
cd ..
