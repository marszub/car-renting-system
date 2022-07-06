// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

contract Rental {
    address private ADMIN_ADDRESS = msg.sender;//the address that put this contract here (though truffle it is the index 0 address)

    uint256 private nextRentalID = 1;//increment after every retnal transaction

    enum RentalStatus{START, END}
    
    struct RentalRecord {
        uint256 rentTime;//from microservice
        uint256 carID;//from microservice
        uint256 userID;//from microservice

        uint256 blockchainTime;//from blockchain
        uint256 rentalID;//from blockchain

        RentalStatus rentalState;// is this start or end of rental
    }

    uint256[] private cars;

    mapping(uint => uint) private rentalMappingStart; //the position in the history rental of the start rental
    mapping(uint => uint) private rentalMappingEnd; //the position in the history rental of the end rental
   
    RentalRecord[] private rentalHistory;

    event addedNewRentalID(uint256 reservationID);


   function addCar(uint256 _carID) public{
        checkAdminPermission();

        if(checkIfCarExists(_carID)){
            revert("Car already exists!");
        }

        cars.push(_carID);

   }

   function getCars()public view returns (uint256[] memory){
    checkAdminPermission();
    return cars;
   }

   function startRental(uint256 _rentTime, uint256 _carID, uint256 _userID)public returns(uint256){
        if(!checkIfCarExists(_carID)){
            revert("Car does not exist!");
        }

        //memory - exists only in function call, temporary variable
        RentalRecord memory record = RentalRecord({
            rentTime : _rentTime,
            carID : _carID,
            userID : _userID,

            blockchainTime : block.timestamp,
            rentalID : nextRentalID,

            rentalState : RentalStatus.START
        });

        rentalHistory.push(record);

        rentalMappingStart[nextRentalID] = rentalHistory.length;//for possible optimization
        nextRentalID += 1;

        emit addedNewRentalID(record.rentalID);//for return value in java TODO - add carID and user ID for the listener

        return record.rentalID;        
   }

   function endRental(uint256 rentalID, uint256 _endRentTime) public{
        if(!checkIfRentalStarted(rentalID)){
            revert("No rental with that ID started");
        }

        if(checkIfRentalEnded(rentalID)){
            revert("Rental with that ID has already ended");
        }

        RentalRecord memory record = rentalHistory[rentalMappingStart[rentalID]-1];

        RentalRecord memory newRecord = RentalRecord({
            rentTime : _endRentTime,
            carID : record.carID,
            userID : record.userID,

            blockchainTime : block.timestamp,
            rentalID : record.rentalID,

            rentalState : RentalStatus.END
        });

        rentalHistory.push(newRecord);

          

        rentalMappingEnd[record.rentalID] = rentalHistory.length ;

   }

   function getActiveRental(uint256 _userID) public view returns(RentalRecord memory)
   {
        for(uint256 i=rentalHistory.length; i>0; i--){
            if(rentalHistory[i-1].userID == _userID){
                if(rentalHistory[i-1].rentalState == RentalStatus.END)
                {
                    revert("No active rental record");
                }else{
                    return rentalHistory[i-1];
                }
            }
        }
        revert("No active rental record");
   }

   function getAllAvailableCars() public view returns(uint256[] memory, bool[] memory)
   {
        bool[] memory result = new bool[](cars.length);
        for(uint256 i=0; i<cars.length; i++){
            if(!checkIfCarRented(cars[i])){
                result[i] = false;
            }
            else{
                result[i] = true;
            }
        }
        return (cars,result);
   }

   
   //help functions

    function checkIfCarExists(uint256 carID) private view returns (bool){ //possible public
        for(uint256 i = 0; i< cars.length; i++)
        {
            if(cars[i] == carID)
            {
                return true;
            }
        }
        return false;
    }

    function checkIfCarRented(uint256 _carID) private view returns (bool){
         for(uint256 i=rentalHistory.length; i>0; i--){
            if(rentalHistory[i-1].carID == _carID){
                if(rentalHistory[i-1].rentalState == RentalStatus.END)
                {
                    return false;
                }else{
                    return true;
                }
            }
        }
        return false;
    }

    function checkIfRentalStarted(uint256 rentalID) private view returns (bool){//possible public
        return !(rentalMappingStart[rentalID]==0);//if it is 0 it means that it does not exist
    } 

    function checkIfRentalEnded(uint256 rentalID)private view returns (bool){
        return !(rentalMappingEnd[rentalID]==0);
    }
    
    function checkAdminPermission() private view {
       if (msg.sender != ADMIN_ADDRESS) {
           revert("Acces denied - no admin access");
       }
   }


}
