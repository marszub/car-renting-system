@echo off
IF "%1"=="-h" (
    ECHO NAME
    ECHO      launch.bat - launch all microservices using docker-compose
    ECHO      NOTE: This script must be run form /car-renting-system directory
    ECHO:
    ECHO SYNOPIS
    ECHO      .\shared\scripts\launch.bat
    ECHO      .\shared\scripts\launch.bat [ARGUMENT]...
    ECHO:
    ECHO DESCRIPTION
    ECHO      -a, -auth                do not run auth
    ECHO      -b, -blockchain          do not run blockchain
    ECHO      -c, -cars, -cardb        do not run cardb
    ECHO      -f, -front, -frontend    do not run frontend
    ECHO      -r, -rental              do not run rental
    ECHO      -t, -tarrif              do not run tarrif
    ECHO      -p, -payment             do not run payment
    exit
)

for %%I IN (%*) DO (IF "%%I"=="-a" GOTO AUTH)
for %%I IN (%*) DO (IF "%%I"=="-auth" GOTO AUTH)
powershell write-host -fore Green Launching auth
call shared/scripts/auth.bat
:AUTH

for %%I IN (%*) DO (IF "%%I"=="-b" GOTO BLOCKCHAIN)
for %%I IN (%*) DO (IF "%%I"=="-blockchain" GOTO BLOCKCHAIN)
powershell write-host -fore Green Launching blockchain
call shared/scripts/blockchain.bat
:BLOCKCHAIN

for %%I IN (%*) DO (IF "%%I"=="-c" GOTO CARDB)
for %%I IN (%*) DO (IF "%%I"=="-cars" GOTO CARDB)
for %%I IN (%*) DO (IF "%%I"=="-cardb" GOTO CARDB)
powershell write-host -fore Green Launching cardb
call shared/scripts/cardb.bat
:CARDB

for %%I IN (%*) DO (IF "%%I"=="-f" GOTO FRONT)
for %%I IN (%*) DO (IF "%%I"=="-front" GOTO FRONT)
for %%I IN (%*) DO (IF "%%I"=="-frontend" GOTO FRONT)
powershell write-host -fore Green Launching frontend
call shared/scripts/front.bat
:FRONT

for %%I IN (%*) DO (IF "%%I"=="-r" GOTO RENTAL)
for %%I IN (%*) DO (IF "%%I"=="-rental" GOTO RENTAL)
powershell write-host -fore Green Launching rental
call shared/scripts/rental.bat
:RENTAL

for %%I IN (%*) DO (IF "%%I"=="-t" GOTO TARRIF)
for %%I IN (%*) DO (IF "%%I"=="-tarrif" GOTO TARRIF)
powershell write-host -fore Green Launching tarrif
call shared/scripts/tarrif.bat
:TARRIF

for %%I IN (%*) DO (IF "%%I"=="-p" GOTO PAYMENT)
for %%I IN (%*) DO (IF "%%I"=="-payment" GOTO PAYMENT)
powershell write-host -fore Green Launching payment
call shared/scripts/payment.bat
:PAYMENT
