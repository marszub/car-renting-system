package pl.edu.agh.carManager.carManager.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import pl.edu.agh.carManager.blockchain.RentalBlockchainProxy;
import pl.edu.agh.carManager.carManager.dto.CarData;
import pl.edu.agh.carManager.carManager.dto.PricingRecord;
import pl.edu.agh.carManager.blockchain.TarrifBlockchainProxy;
import pl.edu.agh.carManager.carManager.enums.CarStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarManagerService {

    private final TarrifBlockchainProxy tarrifBlockchainProxy;
    private final RentalBlockchainProxy rentalBlockchainProxy;

    public CarManagerService(final TarrifBlockchainProxy tarrifBlockchainProxy,
                             final RentalBlockchainProxy rentalBlockchainProxy) {
        this.tarrifBlockchainProxy = tarrifBlockchainProxy;
        this.rentalBlockchainProxy = rentalBlockchainProxy;
    }

    public CarStatus carActivity(int carID) throws Exception {
        return rentalBlockchainProxy.checkCarStatus(BigInteger.valueOf(carID));
    }

    public void addCar(CarData data) {
          //TODO what database?
        return;
    }

    public void removeCar(int carID) {
        //TODO remove from database
        return;
    }

}
