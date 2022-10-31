// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

import "./Tarrif.sol";

contract Rental {
    address private ADMIN_ADDRESS = msg.sender;//the address that put this contract here (though truffle it is the index 0 address)

    enum CarStatus{AVAILABLE, ACTIVE, PARKED, SERVICE}

    struct RentalRecord {
        uint256 startRentTime;//from microservice
        uint256 endRentTime;//firs 0, hen from microservice

        uint256 carID;//from microservice
        uint256 userID;//from microservice

        uint256 blockchainTime;//from blockchain
        uint256 rentalID;//from blockchain

        uint256 rentalPricing;//pricing at the time of starting rental

        uint256[] parkingHistoryStarts;//array of all parking start times
        uint256[] parkingHistoryEnds;//array of all parking end times

    }

    struct Car {
        uint256 carID;
        CarStatus status;
    }

    Car[] private newCars;
    uint256[] cars;//TODO remove after microservices start using the new one
   
    RentalRecord[] private rentalHistory;

    event addedNewRentalID(uint256 reservationID);


    function addCar(uint256 _carID) public{
        checkAdminPermission();

        if(checkIfCarExists(_carID)){
            revert("Car already exists!");
        }

        newCars.push(Car(_carID, CarStatus.AVAILABLE));
        cars.push(_carID);//TODO remove

   }

    //TODO remove 
    function getCars()public view returns (uint256[] memory){
        checkAdminPermission();
        return cars;
    }

    function newGetCars() public view returns (uint256[] memory, CarStatus[] memory){
        checkAdminPermission();
        uint256[] memory carIDs = new uint256[](newCars.length);
        CarStatus[] memory carStatuses = new CarStatus[](newCars.length);
        for(uint256 i = 0; i < newCars.length; i++){
            carIDs[i] = newCars[i].carID;
            carStatuses[i] = newCars[i].status;
        }
        return (carIDs, carStatuses);
    }

   function startRental(uint256 _rentTime, uint256 _carID, uint256 _userID, address _tarrifAddress, uint256 _carTypeID)
   public returns(uint256){
        if(!checkIfCarExists(_carID)){
            revert("Car does not exist!");
        }


        //memory - exists only in function call, temporary variable
        RentalRecord memory record = RentalRecord({
            startRentTime : _rentTime,
            endRentTime: 0,
            carID : _carID,
            userID : _userID,

            blockchainTime : block.timestamp,
            rentalID : rentalHistory.length,
            
            rentalPricing: Tarrif(_tarrifAddress).getCurrentCarPricing(_carTypeID), 

           parkingHistoryStarts: new uint256[](0),
           parkingHistoryEnds: new uint256[](0)

        });

        rentalHistory.push(record);
        newCars[_carID].status = CarStatus.ACTIVE;

        emit addedNewRentalID(record.rentalID);//for return value in java TODO - add carID and user ID for the listener

        return record.rentalID;     
   }

   function startParking(uint256 rentalID, uint256 _startParkingTime) public{
        if(!checkIfRentalStarted(rentalID)){
            revert("No rental with that ID started");
        }

        if(checkIfRentalEnded(rentalID)){
            revert("Rental with that ID has already ended");
        }

        if(checkCarStatus(rentalHistory[rentalID].carID) != CarStatus.ACTIVE){
            revert("Car can not park");
        }


        newCars[rentalHistory[rentalID].carID].status = CarStatus.PARKED;
        rentalHistory[rentalID].parkingHistoryStarts.push(_startParkingTime);
   }

   function endParking(uint256 rentalID, uint256 _endParkingTime) public{
        if(!checkIfRentalStarted(rentalID)){
            revert("No rental with that ID started");
        }

        if(checkIfRentalEnded(rentalID)){
            revert("Rental with that ID has already ended");
        }

        if(checkCarStatus(rentalHistory[rentalID].carID) != CarStatus.PARKED){
            revert("Car is not parked");
        }


        newCars[rentalHistory[rentalID].carID].status = CarStatus.ACTIVE;
        rentalHistory[rentalID].parkingHistoryEnds.push(_endParkingTime);
   }



   function endRental(uint256 rentalID, uint256 _endRentTime) public{
        if(!checkIfRentalStarted(rentalID)){
            revert("No rental with that ID started");
        }

        if(checkIfRentalEnded(rentalID)){
            revert("Rental with that ID has already ended");
        }

        
        rentalHistory[rentalID].endRentTime = _endRentTime;

   }

   function getActiveRental(uint256 _userID) public view returns(RentalRecord memory){
        for(uint256 i=rentalHistory.length; i>0; i--){
            if(rentalHistory[i-1].userID == _userID){
                if(rentalHistory[i-1].endRentTime != 0)
                {
                    revert("No active rental record");
                }else{
                    return rentalHistory[i-1];
                }
            }
        }
        revert("No active rental record");
   }

   function getAllAvailableCars() public view returns(uint256[] memory, bool[] memory){
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

    function checkCarStatus(uint256 carID)public view returns(CarStatus){
        return newCars[carID].status;
    }

   
   //help functions

    function checkIfCarExists(uint256 carID) private view returns (bool){ //possible public
        for(uint256 i = 0; i< newCars.length; i++)
        {
            if(newCars[i].carID == carID)
            {
                return true;
            }
        }
        return false;
    }

    function checkIfCarRented(uint256 _carID) private view returns (bool){
         for(uint256 i=rentalHistory.length; i>0; i--){
            if(rentalHistory[i-1].carID == _carID){
                if(rentalHistory[i-1].endRentTime != 0)
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
        return (rentalID < rentalHistory.length);//maybe different method?
    } 

    function checkIfRentalEnded(uint256 rentalID)private view returns (bool){
        return (rentalID < rentalHistory.length && rentalHistory[rentalID].endRentTime !=0);
    }
    
    function checkAdminPermission() private view {
       if (msg.sender != ADMIN_ADDRESS) {
           revert("Acces denied - no admin access");
       }
   }
}
