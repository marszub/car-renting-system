const Web3 = require("web3")

const web3 = new Web3(new Web3.providers.HttpProvider('http://localhost:5000'))

web3.eth.getBalance("0xEA93B94d5bBEDB14C80BDE2F2a8b97BEd1cD99B0", function(err, result) {
  if (err) {
    console.log(err)
  } else {
    process.stdout.write(web3.utils.fromWei(result, "ether"));
  }
})