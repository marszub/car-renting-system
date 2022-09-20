// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

interface Tarrif {
  enum TarrifStatus{ACTIVE, ENDED}
  
  struct TarrifEntry {
        uint256 carTypeID;//from the carDB
        uint256 pricePerMinute;//or per kilometer, to discuss, or add more
        uint256 blockchainTime;//when this entry was added (from when its valid)
        TarrifStatus status;
  }

  function getFullPricing() external view returns(uint256[] memory, uint256[] memory, uint256[] memory);
  function getCurrentCarPricing(uint256 _carTypeID) external view returns(uint256);
} 


contract TarrifContract is Tarrif{
    address private ADMIN_ADDRESS = msg.sender;//the address that put this contract here (though truffle it is the index 0 address)
    
    TarrifEntry[] private tarrifPricing;

    function addEntry(uint256 _carTypeID, uint256 _pricePerMinute) public{
        checkAdminPermission();
        
        TarrifEntry memory record = TarrifEntry({
            carTypeID : _carTypeID,
            pricePerMinute : _pricePerMinute,
            blockchainTime : block.timestamp,
            status : TarrifStatus.ACTIVE

        });

        tarrifPricing.push(record);
    }

    function removeEntry(uint256 _carTypeID) public{
      checkAdminPermission();

      for(uint256 i = tarrifPricing.length; i>0; i--){
        if(tarrifPricing[i-1].carTypeID == _carTypeID && tarrifPricing[i-1].status == TarrifStatus.ACTIVE){
          tarrifPricing[i-1].status = TarrifStatus.ENDED;
        }
      }
      revert("No entry for that carTypeID");
    }

    function getFullPricing() external view returns(uint256[] memory, uint256[] memory, uint256[] memory){
        //I dont think there is any other way, than to make this three arrays
        //otherwise it breaks, it can not return array of structs
        uint256[] memory carTypesArray = new uint256[](tarrifPricing.length);
        uint256[] memory pricesArray = new uint256[](tarrifPricing.length);
        uint256[] memory timesArray = new uint256[](tarrifPricing.length);
        for(uint256 i=0;i<tarrifPricing.length; i++){
          if(tarrifPricing[i].status == TarrifStatus.ACTIVE){
            carTypesArray[i] = tarrifPricing[i].carTypeID;
            pricesArray[i] = tarrifPricing[i].pricePerMinute;
            timesArray[i] = tarrifPricing[i].blockchainTime;
          }
        }
        return(carTypesArray, pricesArray, timesArray);
    }

    function getCurrentCarPricing(uint256 _carTypeID) external view returns(uint256){
      for(uint256 i = tarrifPricing.length; i>0; i--){
        if(tarrifPricing[i-1].carTypeID == _carTypeID && tarrifPricing[i-1].status == TarrifStatus.ACTIVE){
          return tarrifPricing[i-1].pricePerMinute;
        }
      }
      revert("No entry for that carTypeID");
    }

    function checkAdminPermission() private view {
       if (msg.sender != ADMIN_ADDRESS) {
           revert("Acces denied - no admin access");
       }
   }

}