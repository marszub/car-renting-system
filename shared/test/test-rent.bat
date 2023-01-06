@ECHO OFF
:: type .\shared\test\results\_collumns_eth.csv>.\shared\test\results\rent_eth.csv
for /l %%x in (150, 10, 201) do (
   docker-compose up -d
   timeout 150
   echo | set /p JUNK_VAR1="eth_rent,">>.\shared\test\results\rent_eth.csv
   echo | set /p JUNK_VAR2=%%x>>.\shared\test\results\rent_eth.csv
   echo | set /p JUNK_VAR3=",">>.\shared\test\results\rent_eth.csv
   docker run --rm -v d:\Documents\Studia\praca\car-renting-system\shared\test:/node -w /node node node /node/eth.js >> .\shared\test\results\rent_eth.csv
   echo | set /p JUNK_VAR4=",">>.\shared\test\results\rent_eth.csv
   docker run --rm -i -w /test -v d:\Documents\Studia\praca\car-renting-system\shared\test:/test grafana/k6 run --vus %%x --iterations %%x ./burst-rent.js
   docker run --rm -v d:\Documents\Studia\praca\car-renting-system\shared\test:/node -w /node node node /node/eth.js >> .\shared\test\results\rent_eth.csv
   echo;>>.\shared\test\results\rent_eth.csv
   docker-compose down
)
