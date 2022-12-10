#ifndef RENTAL_ICE
#define RENTAL_ICE

module Rental
{
    exception UnauthorizedRequestException {
        string reason;
    };

    exception RentalDoesNotExistException {
        string reason;
    };

    struct RentalSummaryData {
        long rentalCost;
    };

    interface RentalData
    {
        RentalSummaryData endRental(int userId, string token) throws UnauthorizedRequestException, RentalDoesNotExistException;
    };
};
#endif
