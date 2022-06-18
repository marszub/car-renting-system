package pl.agh.edu.cardatabase.car.service;

import org.springframework.stereotype.Service;
import pl.agh.edu.cardatabase.car.dto.CarData;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.dto.CarList;
import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarData create(final CarInputData data) {
        final Car car = carRepository.save(new Car(data.name()));
        return new CarData(car);
    }

    public CarList getCars() {
        final List<Car> list = carRepository.getCars();
        return new CarList(list.stream().map(CarData::new).toList());
    }
}
