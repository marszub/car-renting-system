package pl.agh.edu.cardatabase.car.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.tuples.generated.Tuple2;
import pl.agh.edu.cardatabase.blockchain.RentalBlockchainProxy;
import pl.agh.edu.cardatabase.car.dto.CarData;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.dto.CarList;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.error.CarCategoryDoesNotExistError;
import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategoryRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;
    private final RentalBlockchainProxy rentalBlockchainProxy;

    public CarService(final CarRepository carRepository,
                      final RentalBlockchainProxy rentalBlockchainProxy,
                      final CarCategoryRepository carCategoryRepository) {
        this.carRepository = carRepository;
        this.rentalBlockchainProxy = rentalBlockchainProxy;
        this.carCategoryRepository = carCategoryRepository;
    }

    @Transactional
    public CarData create(final CarInputData data) throws CarAlreadyExistsError, CarCategoryDoesNotExistError {
        Integer carCategoryId = data.carCategory();
        Optional<CarCategory> carCategory = carCategoryRepository.getCarCategoryById(carCategoryId);
        if(carCategory.isEmpty())
            throw new CarCategoryDoesNotExistError();
        final Car car = carRepository.save(new Car(data.name(), carCategory.get()));
        try {
            rentalBlockchainProxy.addCar(BigInteger.valueOf(car.getId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("Car already exists!")) {
                carRepository.deleteById(car.getId()); //something wrong with blockchain, so delete in DB
                throw new CarAlreadyExistsError();
            }
            carRepository.deleteById(car.getId()); //something wrong with blockchain, so delete in DB
            throw new RuntimeException(e);
        }
        return new CarData(car);
    }

    public CarList getCars() {
        final List<BigInteger> carIDs;
        final List<Boolean> carAvailability;
        try {
            final Tuple2<java.util.List<java.math.BigInteger>, java.util.List<java.lang.Boolean>>
                    res = rentalBlockchainProxy.getAllAvailableCars();
            carIDs  = res.component1();
            carAvailability = res.component2();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        final List<Car> carList = carRepository.getCars();
        for (int i = 0; i < carIDs.size(); i++) {
            if (carAvailability.get(i)) {
                carList.add(carRepository.getById(carIDs.get(i).intValue()));
            }
        }
        return new CarList(carList.stream().map(CarData::new).toList());
    }
}
