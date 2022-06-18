1) Uruchomić docker

docker run --detach --publish 5031:8545 trufflesuite/ganache-cli:latest --mnemonic "frog coin ten lottery sport awful broken extra call stereo police manual"

2)Potem odpalić truffle w katalogu z kontraktami

-jeżeli nie było kompilacji:
 
truffle compile


-żeby zmigrować:

truffle migrate

3)generacja javy

web3j generate truffle --truffle-json=Rental.json --outputDir=./java --package=rentalContract

gdzie --truffle-json = json wygenerwoany przez truffle w folderze build/contracts
--outputDir = folder, do którego ma zostać wstawiony kod javy
--package = nazwa javowego package
