cd ./auth
docker buildx build -t auth:dev --build-context slice=../shared/slice .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up -d auth
:SKIPRUN
cd ..
