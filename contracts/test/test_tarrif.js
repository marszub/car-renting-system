const TestTarrif = artifacts.require("../contracts/Tarrif.sol");

/*
 * uncomment accounts to access the test accounts made available by the
 * Ethereum client
 * See docs: https://www.trufflesuite.com/docs/truffle/testing/writing-tests-in-javascript
 */
contract("TestTarrif", function (/* accounts */) {
  it("should assert true", async function () {
    await TestTarrif.deployed();
    return assert.isTrue(true);
  });
});
