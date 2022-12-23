cd ./contracts
docker build -t blockchain-initializer:dev .
for %%I IN (%*) DO (IF "%%I"=="-R" GOTO SKIPRUN)
docker-compose up contracts
:SKIPRUN
cd ..
