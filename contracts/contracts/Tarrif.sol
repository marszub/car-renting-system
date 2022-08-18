// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

contract Tarrif{
    address private ADMIN_ADDRESS = msg.sender;//the address that put this contract here (though truffle it is the index 0 address)
    
    struct TarrifEntry {
        uint256 carTypeID;//from the carDB
        uint256 pricePerMinute;//or per kilometer, to discuss, or add more
        uint256 blockchainTime;//when this entry was added (from when its valid)
    }

    TarrifEntry[] private tarrifPricing;

    function addEntry(uint256 _carTypeID, uint256 _pricePerMinute) public{
        checkAdminPermission();
        
        TarrifEntry memory record = TarrifEntry({
            carTypeID : _carTypeID,
            pricePerMinute : _pricePerMinute,
            blockchainTime : block.timestamp

        });

        tarrifPricing.push(record);
    }



    function getPricing() public view returns(uint256[] memory,uint256[] memory,uint256[] memory){
        //I dont think there is any other way, than to make this three arrays
        //otherwise it breaks, it can not return array of structs
        uint256[] memory carTypesArray = new uint256[](tarrifPricing.length);
        uint256[] memory pricesArray = new uint256[](tarrifPricing.length);
        uint256[] memory timesArray = new uint256[](tarrifPricing.length);
        for(uint256 i=0;i<tarrifPricing.length; i++){
            carTypesArray[i]=tarrifPricing[i].carTypeID;
            pricesArray[i]=tarrifPricing[i].pricePerMinute;
            timesArray[i]=tarrifPricing[i].blockchainTime;
        }
        return(carTypesArray, pricesArray, timesArray);
    }

    function checkAdminPermission() private view {
       if (msg.sender != ADMIN_ADDRESS) {
           revert("Acces denied - no admin access");
       }
   }

}