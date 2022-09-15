cd ./contracts
docker run --detach --publish 5000:8545 trufflesuite/ganache-cli:latest --mnemonic "frog coin ten lottery sport awful broken extra call stereo police manual"

docker build -t blockchain-initializer:dev .
docker run --detach --rm -v "%cd%":/contracts blockchain-initializer:dev