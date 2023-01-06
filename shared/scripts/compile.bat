@echo off

powershell write-host -fore Green Compiling auth
call shared/scripts/auth.bat -R

powershell write-host -fore Green Compiling blockchain
call shared/scripts/blockchain.bat -R

powershell write-host -fore Green Compiling cardb
call shared/scripts/cardb.bat -R

powershell write-host -fore Green Compiling frontend
call shared/scripts/front.bat -R

powershell write-host -fore Green Compiling rental
call shared/scripts/rental.bat -R

powershell write-host -fore Green Compiling tarrif
call shared/scripts/tarrif.bat -R

powershell write-host -fore Green Compiling payment
call shared/scripts/payment.bat -R

powershell write-host -fore Green Compiling carManager
call shared/scripts/carmanager.bat -R
