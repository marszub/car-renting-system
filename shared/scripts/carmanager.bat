cd ./carManager
docker buildx build -t carmanager:dev --build-context slice=../shared/slice .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up -d carManager
:SKIPRUN
cd ..
