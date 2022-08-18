const Rental = artifacts.require("../contracts/Rental.sol");
const chai = require('chai')
const chaiAsPromised = require('chai-as-promised');



contract("Rental", accounts => {
    const creatorAddress = accounts[0];
    before(async () => {
      this.rentalTest = await Rental.deployed();
    });
  
    it('should deploy successfully', async () => {
      assert.notEqual(creatorAddress, 0x0);
      assert.notEqual(creatorAddress, '');
      assert.notEqual(creatorAddress, null);
      assert.notEqual(creatorAddress, undefined);
    });
  
    it('should add two cars and return them', async () => {
        await this.rentalTest.addCar(1);
        await this.rentalTest.addCar(2);

        const result = await this.rentalTest.getCars();

        assert.equal(result[0],1);
        assert.equal(result[1],2);
    });

    it("should not another car with the same ID", async () => {
      return this.rentalTest.addCar(1).then(() => {
        assert.ok(false, "It didn't fail");
      }, () => {
        assert.ok(true, "Passed");
      });
    });

    it("should add active rental and get it", async () => {
        await this.rentalTest.startRental(10,1,1);

        const result = await this.rentalTest.getActiveRental(1)

        assert.equal(result.rentTime, 10);
    });

    it("should end rental and fail at getting it", async () => {
      return this.rentalTest.endRental(1).then(() => {
        assert.ok(false, "It didn't fail");
      }, () => {
        assert.ok(true, "Passed");
      });
    });

    
  });
