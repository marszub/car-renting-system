package pl.agh.edu.cardatabase.carCategory.ice;

import cardb.CarPosition;
import cardb.CarPositionUpdater;
import cardb.UnauthorizedRequestException;
import com.zeroc.Ice.Current;
import org.springframework.core.env.Environment;
import org.springframework.transaction.support.TransactionTemplate;
import pl.agh.edu.cardatabase.car.dto.CarLocationUpdateInput;
import pl.agh.edu.cardatabase.car.error.CarDoesNotExistError;
import pl.agh.edu.cardatabase.car.service.CarService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CarPositionUpdaterServant implements CarPositionUpdater {
    private final CarService carService;
    private final String secret;

    private TransactionTemplate template;


    public CarPositionUpdaterServant(final Environment environment, final CarService carService, final TransactionTemplate template) {
        this.carService = carService;
        this.secret = environment.getProperty("ice.carPositionUpdater.secret");
        this.template = template;
    }

    @Override
    public void updateCarsPosition(CarPosition[] list, String token, Current current) throws UnauthorizedRequestException {
        String convertedList = changeCarPositionListToString(list);
        if(!validateRequest(convertedList, token)) {
            throw new UnauthorizedRequestException();
        }
        Arrays.stream(list).forEach(this::updatePosition);
        System.out.println("AAA");
        System.out.println(convertedList);
        System.out.println(token);
        System.out.println("AAA");
    }

    void updatePosition(CarPosition carPosition) {
        template.execute(status -> {
            CarLocationUpdateInput carLocationUpdateInput =
                    new CarLocationUpdateInput(carPosition.position.latitude, carPosition.position.longitude);
            try {
                carService.updateCarLocation(carLocationUpdateInput, carPosition.carId);
            } catch (CarDoesNotExistError e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    private String changeCarPositionListToString(CarPosition[] list) {
        StringBuilder value = new StringBuilder();
        for (CarPosition carPosition : list) {
            value
                    .append("$")
                    .append(carPosition.carId)
                    .append(";")
                    .append(carPosition.position.latitude)
                    .append(";")
                    .append(carPosition.position.longitude)
                    .append(";")
                    .append(carPosition.timestamp)
                    .append("$");
        }
        return value.toString();
    }

    private boolean validateRequest(String data, String token) {
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
