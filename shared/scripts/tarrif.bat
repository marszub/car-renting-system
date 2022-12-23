cd ./tarrif
docker buildx build -t tarrif:dev --build-context slice=../shared/slice .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up -d tarrif
:SKIPRUN
cd ..
