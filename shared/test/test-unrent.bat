@ECHO OFF
:: type .\shared\test\results\_collumns_eth.csv>.\shared\test\results\unrent_eth.csv
for /l %%x in (190, 10, 191) do (
   docker-compose up -d
   timeout 150
   echo | set /p JUNK_VAR1="eth_unrent,">>.\shared\test\results\unrent_eth.csv
   echo | set /p JUNK_VAR2=%%x>>.\shared\test\results\unrent_eth.csv
   echo | set /p JUNK_VAR3=",">>.\shared\test\results\unrent_eth.csv
   docker run --rm -v d:\Documents\Studia\praca\car-renting-system\shared\test:/node -w /node node node /node/eth.js >> .\shared\test\results\unrent_eth.csv
   echo | set /p JUNK_VAR4=",">>.\shared\test\results\unrent_eth.csv
   docker run --rm -i -w /test -v d:\Documents\Studia\praca\car-renting-system\shared\test:/test grafana/k6 run --vus %%x --iterations %%x ./burst-unrent.js
   docker run --rm -v d:\Documents\Studia\praca\car-renting-system\shared\test:/node -w /node node node /node/eth.js >> .\shared\test\results\unrent_eth.csv
   echo;>>.\shared\test\results\unrent_eth.csv
   docker-compose down
)
