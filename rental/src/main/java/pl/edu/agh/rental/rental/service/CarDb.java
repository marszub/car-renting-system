package pl.edu.agh.rental.rental.service;

import Auth.AccessData;
import Auth.AccountPrx;
import Auth.Role;
import Auth.TokenVerificationStatus;
import cardb.CarPrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.agh.rental.auth.User;

import java.util.List;

public class CarDb {
    @Autowired
    private Communicator communicator;

    public int getCarCategory(int carId) {
        ObjectPrx baseAccount = communicator.stringToProxy("car/" + carId + ":" + communicator.getProperties().getProperty("Car.Proxy"));
        CarPrx car = CarPrx.checkedCast(baseAccount);
        if (car == null) throw new Error("Invalid proxy");

        return car.getCategory();
    }
}
