for /l %%x in (50, 50, 100000) do (
   type c:\Users\givrox7\Documents\Projects\car-renting-system\shared\test\admin-login.js | docker run --rm -i -v c:\Users\givrox7\Documents\Projects\car-renting-system\shared\test\results:/results grafana/k6 run --vus %%x --duration 120s -
)
