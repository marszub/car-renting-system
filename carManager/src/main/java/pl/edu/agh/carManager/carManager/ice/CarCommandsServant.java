package pl.edu.agh.carManager.carManager.ice;

import CarManager.CarCommands;
import CarManager.UnauthorizedRequestException;
import cardb.CarPosition;
import com.zeroc.Ice.Current;
import org.springframework.core.env.Environment;
import pl.edu.agh.carManager.carManager.dto.CarInput;
import pl.edu.agh.carManager.carManager.error.DatabaseConflictError;
import pl.edu.agh.carManager.carManager.service.CarManagerService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CarCommandsServant implements CarCommands {
    private final CarManagerService carManagerService;
    private final String secret;

    public CarCommandsServant(CarManagerService carManagerService, final Environment environment) {
        this.carManagerService = carManagerService;
        this.secret = environment.getProperty("ice.carManager.secret");
    }

    @Override
    public void openCar(int carId, String token, Current current) throws UnauthorizedRequestException {
        if(!validateToken(String.valueOf(carId), token)) {
            throw new UnauthorizedRequestException();
        }
        carManagerService.openCar(carId);
    }

    private boolean validateToken(final String data, String token) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(md == null) {
            return false;
        }
        md.update((data + this.secret).getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        System.out.println("-------MD5-----" + sb + "\n\n");

        return sb.toString().equalsIgnoreCase(token);
    }
}
