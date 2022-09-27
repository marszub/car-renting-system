const Migrations = artifacts.require("Migrations");
const Rental = artifacts.require("Rental");
const TarrifContract = artifacts.require("TarrifContract");

module.exports = function (deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(Rental);
  deployer.deploy(TarrifContract);
};
