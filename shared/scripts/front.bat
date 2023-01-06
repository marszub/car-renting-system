cd ./user-frontend
docker build -t front:dev .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up -d front
:SKIPRUN
cd ..
