const Rental = artifacts.require("../contracts/Rental.sol");
const TarrifContract = artifacts.require("TarrifContract");
const chai = require('chai')
const chaiAsPromised = require('chai-as-promised');

contract("Rental", accounts => {
    const creatorAddress = accounts[0];
    before(async () => {
      this.rentalTest = await Rental.deployed();
      this.tarrifHelp = await TarrifContract.deployed();
      this.tarrifHelp.addEntry(1, 10);
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

    //this one is from web, passes at ANY error
    it("should not add another car with the same ID", async () => {
      return this.rentalTest.addCar(1).then(() => {
        assert.ok(false, "It didn't fail");
      }, () => {
        assert.ok(true, "Passed");
      });
    });

    it("should add active rental and get it", async () => {
       // await this.tarrifHelp.addEntry(1,10);
        const emit_event = await this.rentalTest.startRental(10, 1, 1, this.tarrifHelp.address, 1);
        const returned_id = emit_event.logs[0].args[0].toNumber();//the id of the rental
        assert.equal(returned_id, 1)//should be 1, the first rental

        const result = await this.rentalTest.getActiveRental(1)
        assert.equal(result.rentTime, 10);
    });

    //this one is from web, passes at ANY error
    it("should end rental and fail at getting it", async () => {
      await this.rentalTest.endRental(1, 20)

      return this.rentalTest.getActiveRental(1).then(() => {
        assert.ok(false, "It didn't fail");
      }, () => {
        assert.ok(true, "Passed");
      });
    });
      
    //this one is from web, passes at ANY error
    it("should fail at ending the same rental second time", async () => {

      return this.rentalTest.endRental(1).then(() => {
        assert.ok(false, "It didn't fail");
      }, () => {
        assert.ok(true, "Passed");
      });
    });

    it("should return two cars (array of three, with two marked as free (so false)), after one has been rented", async () => {
      await this.rentalTest.addCar(3);
      await this.rentalTest.startRental(10, 1, 1, this.tarrifHelp.address, 1);

      const result = await this.rentalTest.getAllAvailableCars();
      //cars
      assert.equal(result[0][0], 1);
      assert.equal(result[0][1], 2);
      assert.equal(result[0][2], 3);
      //availability
      assert.equal(result[1][0], true);
      assert.equal(result[1][1], false);
      assert.equal(result[1][2], false); 
  });
});
