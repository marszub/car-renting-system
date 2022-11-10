package pl.edu.agh.rental.rental.service;

import cardb.CarPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import org.springframework.stereotype.Component;

@Component
public class CarDb {
    private Communicator communicator;

    public CarDb(Communicator communicator) {
        this.communicator = communicator;
    }

    public int getCarCategory(int carId) {
        ObjectPrx baseAccount = communicator.stringToProxy("car/" + carId + ":" + communicator.getProperties().getProperty("Car.Proxy"));
        CarPrx car = CarPrx.checkedCast(baseAccount);
        if (car == null) throw new Error("Invalid proxy");

        return car.getCategory();
    }
}
