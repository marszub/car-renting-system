const Tarrif = artifacts.require("../contracts/Tarrif.sol");

contract("Tarrif", accounts => {
  const creatorAddress = accounts[0];
  before(async () => {
    this.tarrifTest = await Tarrif.deployed();
  });

  it('should deploy successfully', async () => {
    assert.notEqual(creatorAddress, 0x0);
    assert.notEqual(creatorAddress, '');
    assert.notEqual(creatorAddress, null);
    assert.notEqual(creatorAddress, undefined);
  });

  it('should add one pricing and return it', async()=>{
    await this.tarrifTest.addEntry(1, 10);
    const result = await  this.tarrifTest.getPricing();

    assert.equal(result[0][0], 1);
    assert.equal(result[1][0], 10);
  });

  it('should add complex pricing and return them', async()=>{
   //entry (1,10) from previous test
    await this.tarrifTest.addEntry(2, 20);
    await this.tarrifTest.addEntry(3, 30);
    const result = await this.tarrifTest.getPricing();

    assert.equal(result[0][0], 1);
    assert.equal(result[1][0], 10);

    assert.equal(result[0][1], 2);
    assert.equal(result[1][1], 20);

    assert.equal(result[0][2], 3);
    assert.equal(result[1][2], 30);
  });
});
