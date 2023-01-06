cd ./payment
docker buildx build -t payment:dev --build-context slice=../shared/slice .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up -d payment
:SKIPRUN
cd ..
