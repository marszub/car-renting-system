package pl.edu.agh.carManager.carManager.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.carManager.carManager.dto.CarData;
import pl.edu.agh.carManager.carManager.dto.CarDataList;
import pl.edu.agh.carManager.carManager.dto.CarInput;
import pl.edu.agh.carManager.carManager.error.CarDoesNotExistError;
import pl.edu.agh.carManager.carManager.error.DatabaseConflictError;
import pl.edu.agh.carManager.carManager.persistence.Car;
import pl.edu.agh.carManager.carManager.persistence.CarRepository;

import java.util.List;

@Service
public class CarManagerService {
    private final CarsMap carsMap;
    private final ConnectionService connectionService;
    private final CarRepository carRepository;

    public CarManagerService(final CarsMap carsMap, final ConnectionService connectionService, final CarRepository carRepository) {
        this.carsMap = carsMap;
        this.connectionService = connectionService;
        this.carRepository = carRepository;
    }

    public void createCar(CarInput data) throws DatabaseConflictError {
        carsMap.addCar(data.carId(), data.carToken());
    }

    public CarDataList getCars() {
        List<Car> cars = carRepository.getAllCars();
        return new CarDataList(cars.stream().map(CarData::new).toList());
    }

    public void openCar(Integer carId) {
        try {
            connectionService.sendMessage("OPEN THE CAR!", carId);
        } catch (CarDoesNotExistError e) {
            System.out.println("CAR DOES NOT EXIST!");
            e.printStackTrace();
        }
    }
}

