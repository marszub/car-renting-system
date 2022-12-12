package pl.edu.agh.carManager.carManager.service;

import cardb.CarPosition;
import cardb.CarPositionUpdaterPrx;
import cardb.UnauthorizedRequestException;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Component
public class PositionAccumulator {
    private List<CarPosition> positionList;
    private Integer maxListSize;
    private final Communicator communicator;
    private final String secret;

    public PositionAccumulator(Communicator communicator, Environment environment) {
        this.positionList = new LinkedList<>();
        this.maxListSize = Integer.valueOf(Objects.requireNonNull(environment.getProperty("ice.position_batch_size")));
        this.communicator = communicator;
        this.secret = environment.getProperty("ice.carPositionUpdater.secret");
    }

    public void addPosition(CarPosition position) throws UnauthorizedRequestException {
        positionList.add(position);
        if(positionList.size() == this.maxListSize) {
            ObjectPrx positionUpdater = communicator.stringToProxy("positionUpdater/positionUpdater:" + communicator.getProperties().getProperty("Car.Proxy"));
            CarPositionUpdaterPrx positionUpdaterPrx = CarPositionUpdaterPrx.checkedCast(positionUpdater);
            if(positionUpdaterPrx == null) throw new Error("Invalid proxy");

            CarPosition[] list = positionList.toArray(new CarPosition[0]);

            positionUpdaterPrx.updateCarsPosition(list, createToken(list));
            positionList = new LinkedList<>();
        }
    }

    private String createToken(CarPosition[] list) {
        String data = changeCarPositionListToString(list);
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
}
