for /l %%x in (100, 100, 5001) do (
   docker run --rm -i -w /test -v d:\Documents\Studia\praca\car-renting-system\shared\test:/test grafana/k6 run --vus %%x --iterations %%x ./burst-pricing.js
)
