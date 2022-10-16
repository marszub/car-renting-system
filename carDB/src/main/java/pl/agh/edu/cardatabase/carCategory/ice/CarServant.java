package pl.agh.edu.cardatabase.carCategory.ice;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategoryRepository;

import java.util.NoSuchElementException;

public class CarServant implements cardb.Car {
    @Autowired
    private CarCategoryRepository carCategories;
    @Autowired
    private CarRepository cars;

    @Override
    public int getCategory(Current current) {
        try {
            int carId = Integer.parseInt(current.id.name);
            return carCategories.getCarCategoryById(carId).get().getId();
        } catch (Exception e) {
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
