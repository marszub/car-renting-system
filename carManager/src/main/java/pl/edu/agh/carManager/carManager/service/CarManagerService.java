package pl.edu.agh.carManager.carManager.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.carManager.carManager.dto.CarInput;
import pl.edu.agh.carManager.carManager.error.DatabaseConflictError;

@Service
public class CarManagerService {
    private final CarsMap carsMap;
    private final ConnectionService connectionService;

    public CarManagerService(final CarsMap carsMap, final ConnectionService connectionService) {
        this.carsMap = carsMap;
        this.connectionService = connectionService;
    }

    public void createCar(CarInput data) throws DatabaseConflictError {
        carsMap.addCar(data.carId(), data.carToken());
    }
}

