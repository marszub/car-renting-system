@ECHO OFF
for /l %%x in (100, 100, 101) do (
    timeout 1
    echo|set/pJUNK=,>>.\shared\test\results\rent_eth.csv
    type .\shared\test\results\_collumns_eth.csv>>.\shared\test\results\rent_eth.csv
)