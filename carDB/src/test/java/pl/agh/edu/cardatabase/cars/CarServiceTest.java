package pl.agh.edu.cardatabase.cars;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.tuples.generated.Tuple2;
import pl.agh.edu.cardatabase.blockchain.RentalBlockchainProxy;
import pl.agh.edu.cardatabase.car.dto.CarData;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.dto.CarList;
import pl.agh.edu.cardatabase.car.dto.CarLocationUpdateInput;
import pl.agh.edu.cardatabase.car.dto.Coordinates;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.error.CarCategoryDoesNotExistError;
import pl.agh.edu.cardatabase.car.error.CarDoesNotExistError;
import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.car.service.CarService;
import pl.agh.edu.cardatabase.carCategory.ice.CarManagerCommunicator;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;
import pl.agh.edu.cardatabase.carCategory.persistence.CarCategoryRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private RentalBlockchainProxy rentalBlockchainProxy;

    @Mock
    private CarCategoryRepository carCategoryRepository;

    @Mock
    private CarManagerCommunicator carManagerCommunicator;

    private CarService carService;
    private CarCategory carCategory = new CarCategory("Category");

    private final String carName1 = "CarName1";
    private final String carName2 = "CarName2";
    private final Car completeCar1 = new Car(carName1, carCategory);
    private final Car completeCar2 = new Car(carName2, carCategory);
    private final BigInteger[] intArray = {BigInteger.valueOf(0),
                                           BigInteger.valueOf(1),
                                           BigInteger.valueOf(2)};
    private final Boolean[] booleanArray = {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    private final List<BigInteger> intList = Lists.newArrayList(intArray);
    private final List<Boolean> booleanList = Lists.newArrayList(booleanArray);
    private final Optional<CarCategory> optionalCarCategory = Optional.of(new CarCategory("Category"));
    private final Optional<CarCategory> emptyOptionalCarCategory = Optional.empty();


    @BeforeEach
    void setUp() {
        completeCar1.setId(0);
        completeCar2.setId(1);
        when(carRepository.save(any(Car.class))).thenReturn(completeCar1);
        when(carRepository.getById(0)).thenReturn(completeCar1);
        when(carRepository.getById(1)).thenReturn(completeCar2);
        when(carRepository.getById(3)).thenThrow(new EntityNotFoundException());
    }

    @Test
    @Transactional
    void checkAddCarSuccess() throws Exception {
        when(rentalBlockchainProxy.addCar(any())).thenReturn(null);
        when(carCategoryRepository.getCarCategoryById(1)).thenReturn(optionalCarCategory);
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        try {
            final CarData car = carService.create(new CarInputData("Test", 1));
            assertThat(car.carName()).isEqualTo(carName1);
            assertThat(car.id()).isEqualTo(0);
        } catch (CarAlreadyExistsError e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void checkAddCarFailureNoCarCategoryError() throws Exception {
        when(rentalBlockchainProxy.addCar(any())).thenReturn(null);
        when(carCategoryRepository.getCarCategoryById(1)).thenReturn(emptyOptionalCarCategory);
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        assertThatExceptionOfType(CarCategoryDoesNotExistError.class)
                .isThrownBy(() -> carService.create(new CarInputData("Test1", 1)));
    }

    @Test
    @Transactional
    void checkAddCarFailureBlockchainError() throws Exception {
        when(rentalBlockchainProxy.addCar(any())).thenThrow(new Exception());
        when(carCategoryRepository.getCarCategoryById(1)).thenReturn(optionalCarCategory);
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> carService.create(new CarInputData("Test1", 1)));
    }

    @Test
    @Transactional
    void checkAddCarFailureCarAlreadyExists() throws Exception {
        when(rentalBlockchainProxy.addCar(any()))
                .thenThrow(new Exception("Car already exists!"));
        when(carCategoryRepository.getCarCategoryById(1)).thenReturn(optionalCarCategory);
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        assertThatExceptionOfType(CarAlreadyExistsError.class)
                .isThrownBy(() -> carService.create(new CarInputData("Test2", 1)));
    }

    @Test
    @Transactional
    void getCarsSuccess() throws Exception {
        when(rentalBlockchainProxy.getAllAvailableCars())
                .thenReturn(new Tuple2<>(intList, booleanList));
        when(carCategoryRepository.getCarCategoryById(1)).thenReturn(optionalCarCategory);
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        final CarList list = carService.getAvailableCars();
        assertThat(list.cars().size()).isEqualTo(2);
        assertThat(list.cars().get(0).carName()).isEqualTo(carName1);
        assertThat(list.cars().get(1).carName()).isEqualTo(carName2);
    }

    @Test
    @Transactional
    void getCarsFailure() throws Exception {
        when(rentalBlockchainProxy.getAllAvailableCars()).thenThrow(new Exception());
        when(carCategoryRepository.getCarCategoryById(1)).thenReturn(optionalCarCategory);
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> carService.getAvailableCars());
    }

    @Test
    @Transactional
    void updateCarLocationCarExists() throws CarDoesNotExistError {
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        CarLocationUpdateInput input = new CarLocationUpdateInput(new Coordinates(1.00, 2.00));
        assertThatNoException().isThrownBy(() -> carService.updateCarLocation(input, 0));
    }

    @Test
    @Transactional
    void updateCarLocationCarDoesNotExist() throws CarDoesNotExistError {
        this.carService = new CarService(carRepository, rentalBlockchainProxy, carCategoryRepository, carManagerCommunicator);
        CarLocationUpdateInput input = new CarLocationUpdateInput(new Coordinates(1.00, 2.00));
        assertThatExceptionOfType(CarDoesNotExistError.class)
                .isThrownBy(() -> carService.updateCarLocation(input, 3));
    }
}
