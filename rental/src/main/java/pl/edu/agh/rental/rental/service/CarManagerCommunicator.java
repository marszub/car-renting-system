package pl.edu.agh.rental.rental.service;

import CarManager.CarCommandsPrx;
import CarManager.UnauthorizedRequestException;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class CarManagerCommunicator {
    private final Communicator communicator;
    private final String secret;

    public CarManagerCommunicator(Communicator communicator, Environment environment) {
        this.communicator = communicator;
        this.secret = environment.getProperty("ice.carManager.secret");
    }

    public void openCar(final int carId) {
        ObjectPrx baseAccount = communicator.stringToProxy("carManager/carCommands:" + communicator.getProperties().getProperty("CarManager.Proxy"));
        CarCommandsPrx car = CarCommandsPrx.checkedCast(baseAccount);

        if (car == null) throw new Error("Invalid proxy");

        try {
            car.openCar(carId, createToken(String.valueOf(carId)));
        } catch (UnauthorizedRequestException e) {
            System.out.println("AUTHORIZATION ERROR!");
            e.printStackTrace();
        }
    }

    private String createToken(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md == null) {
            return "";
        }
        md.update((data + this.secret).getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        System.out.println("-------MD5-----" + sb + "\n\n");

        return sb.toString();
    }
}
