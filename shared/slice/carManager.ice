#ifndef CARMANAGER_ICE
#define CARMANAGER_ICE

module CarManager
{
    exception UnauthorizedRequestException {
        string reason;
    };

    interface CarCommands
    {
        void openCar(int carId, string token) throws UnauthorizedRequestException;
    };
};
#endif
