package pl.agh.edu.cardatabase.car.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.tuples.generated.Tuple2;
import pl.agh.edu.cardatabase.blockchain.RentalBlockchainProxy;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.dto.CarList;
import pl.agh.edu.cardatabase.car.dto.CarLocationUpdateInput;
import pl.agh.edu.cardatabase.car.dto.CarData;
import pl.agh.edu.cardatabase.car.dto.AdminCarData;
import pl.agh.edu.cardatabase.car.dto.AdminCarList;
import pl.agh.edu.cardatabase.car.dto.CarStatus;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.error.CarCategoryDoesNotExistError;
import pl.agh.edu.cardatabase.car.error.CarDoesNotExistError;
import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
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

    public CarList getAvailableCars() {
        final List<BigInteger> carIDs;
        final List<Boolean> carAvailability;
        try {
            final Tuple2<List<BigInteger>, List<Boolean>>
                    res = rentalBlockchainProxy.getAllAvailableCars();
            carIDs  = res.component1();
            carAvailability = res.component2();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        final List<Car> carList = new ArrayList<>();
        for (int i = 0; i < carIDs.size(); i++) {
            if (carAvailability.get(i)) {
                carList.add(carRepository.getById(carIDs.get(i).intValue()));
            }
        }
        return new CarList(carList.stream().map(CarData::new).toList());
    }

    //for admin
    public AdminCarList adminGetCars() {
        final List<BigInteger> carIDs;
        final List<CarStatus> carStatuses;
        try{
            final Tuple2<List<BigInteger>,List<BigInteger>>
                    res = rentalBlockchainProxy.adminGetCars();
            carIDs = res.component1();
            carStatuses = res.component2().stream().map(object -> CarStatus.values()[object.intValue()]).toList();

            List<AdminCarData> resultList = new ArrayList<>();

            for(int i = 0; i < carIDs.size(); i++){
                resultList.add(new AdminCarData(carRepository.getById(carIDs.get(i).intValue()), carStatuses.get(i)));
            }
            return new AdminCarList(resultList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public void updateCarLocation(CarLocationUpdateInput data, Integer id) throws CarDoesNotExistError {
        try {
            carRepository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new CarDoesNotExistError();
        }
        carRepository.updateCarLocation(id, data.coordinates().latitude(), data.coordinates().longitude());
    }
}
