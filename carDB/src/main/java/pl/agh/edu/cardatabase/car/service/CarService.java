package pl.agh.edu.cardatabase.car.service;

import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import pl.agh.edu.cardatabase.car.dto.CarData;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.dto.CarList;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.rentalContract.Rental;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Service
public class CarService {
    private final CarRepository carRepository;

    private Web3j web3client;//connection with blockchain
    private ContractGasProvider gasProvider;//data about the costs in blockchain
    private Credentials credentials;//admin Credentials in blockchain
    private Rental adminRentalService;

    private static final String CONTRACT_ADDRESS =
            "0xee3e92973010664a804bf96188ac4766fb84a3b9";//TODO - change to config
    private static final String ADMIN_PRIVATE_KEY =
            "0x23901d28534eda9518308ce5cfea39b04b91a0518ceea6f3406b6c1ed8201e6a";//TODO - change to config

    private static final String BLOCKCHAIN_ADDRESS = "HTTP://0.0.0.0:5031/";//TODO - change to config

    public CarService(final CarRepository carRepository) {
        this.carRepository = carRepository;
        this.web3client =
                Web3j.build(new HttpService(BLOCKCHAIN_ADDRESS));//where blockchain is TODO - change to config

        this.gasProvider =
                new StaticGasProvider(BigInteger.valueOf(2000000000), BigInteger.valueOf(6721975));//constant values

        this.credentials =
                Credentials.create(ADMIN_PRIVATE_KEY);//choosing the account by its private key

        this.adminRentalService =
                Rental.load(CONTRACT_ADDRESS, web3client, credentials, gasProvider);//object used to call contracts
    }

    public CarData create(final CarInputData data) throws CarAlreadyExistsError {
        final Car car = carRepository.save(new Car(data.name()));
        try{
            adminRentalService.addCar(BigInteger.valueOf(car.getId())).send();
        }catch(Exception e){
            System.out.println(e.getMessage());
            if(Objects.equals(e.getMessage(),
                    "Error processing transaction request: VM Exception while processing transaction:" +
                            " revert Car already exists!")){
                carRepository.deleteById(car.getId());//something wrong with blockchain, so delete in DB
                throw new CarAlreadyExistsError();
            }
            carRepository.deleteById(car.getId());//something wrong with blockchain, so delete in DB
            throw new RuntimeException(e);
        }
        return new CarData(car);
    }

    public CarList getCars() {
        List carIDs;
        try{
            carIDs = adminRentalService.getCars().send();
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println(carIDs);//TODO - I don't know if carIDs form blockchain are needed here,
        final List<Car> list = carRepository.getCars();
        return new CarList(list.stream().map(CarData::new).toList());
    }
}
