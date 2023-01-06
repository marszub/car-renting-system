for /l %%x in (1800, 100, 1801) do (
   docker-compose up -d
   timeout 90
   docker run --rm -i -w /test -v d:\Documents\Studia\praca\car-renting-system\shared\test:/test grafana/k6 run --vus %%x --iterations %%x ./burst-tariff.js
   docker-compose down
)
