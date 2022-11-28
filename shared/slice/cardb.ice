#ifndef CARDB_ICE
#define CARDB_ICE

module cardb
{
    interface Car
    {
        idempotent int getCategory();
    };

    exception CarDoesNotExistException {
        string reason;
    };

    exception UnauthorizedRequestException {
        string reason;
    };

    struct Position {
        double latitude;
        double longitude;
    };
    
    struct CarPosition {
        int carId;
        Position position;
        long timestamp;
    };

    sequence <CarPosition> CarPositionList;

    interface CarPositionUpdater
    {
        void updateCarsPosition(CarPositionList list, string token) throws CarDoesNotExistException, UnauthorizedRequestException;
    };
};
#endif
