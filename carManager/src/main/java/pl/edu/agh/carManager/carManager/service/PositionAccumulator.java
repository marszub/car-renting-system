package pl.edu.agh.carManager.carManager.service;

import cardb.CarPosition;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import org.springframework.core.env.Environment;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PositionAccumulator {
    private List<CarPosition> positionList;
    private Integer maxListSize;
    private final Communicator communicator;

    public PositionAccumulator(Communicator communicator, Environment environment) {
        this.positionList = new LinkedList<>();
        this.maxListSize = Integer.valueOf(Objects.requireNonNull(environment.getProperty("ice.position_batch_size")));
        this.communicator = communicator;
    }

    public void addPosition(CarPosition position) {
        positionList.add(position);
        if(positionList.size() == this.maxListSize) {
            ObjectPrx positionUpdater = communicator.stringToProxy("positionUpdater/positionUpdater:" + )
        }
    }
}
