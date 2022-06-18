const Migrations = artifacts.require("Migrations");
const Rental = artifacts.require("Rental");

module.exports = function (deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(Rental);
};
