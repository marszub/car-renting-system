const Migrations = artifacts.require("Migrations");
const Rental = artifacts.require("Rental");
const Tarrif = artifacts.require("Tarrif");

module.exports = function (deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(Rental);
  deployer.deploy(Tarrif);
};
