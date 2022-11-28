package pl.agh.edu.cardatabase.carCategory.ice;

import cardb.CarDoesNotExistException;
import cardb.CarPosition;
import cardb.CarPositionUpdater;
import cardb.UnauthorizedRequestException;
import com.zeroc.Ice.Current;
import pl.agh.edu.cardatabase.car.service.CarService;

import java.util.Arrays;

public class CarPositionUpdaterServant implements CarPositionUpdater {
    private final CarService carService;

    public CarPositionUpdaterServant(final CarService carService) {
        this.carService = carService;
    }

    @Override
    public void updateCarsPosition(CarPosition[] list, String token, Current current) throws CarDoesNotExistException, UnauthorizedRequestException {
        System.out.println("CONNECTED!");
        System.out.println(Arrays.toString(list));
    }
}
