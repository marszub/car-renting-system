package pl.agh.edu.cardatabase.carCategory.ice;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectNotExistException;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;

import java.util.Optional;

public class CarServant implements cardb.Car {
    private final CarRepository cars;

    public CarServant(CarRepository carRepository) {
        this.cars = carRepository;
    }

    @Override
    public int getCategory(Current current) {
        try {
            int carId = Integer.parseInt(current.id.name);
            Optional<Integer> carCategoryId = cars.getCarCategoryId(carId);
            return carCategoryId.orElse(-1);
        } catch (Exception e) {
        System.out.println(e.getMessage());
            throw new ObjectNotExistException();
        }
    }

    @Override
    public void ice_ping(Current current)
    {
        try {
            int carId = Integer.parseInt(current.id.name);
            cars.getById(carId);
        } catch (Exception e) {
            throw new ObjectNotExistException();
        }
    }
}
