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
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.car.service.CarService;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private RentalBlockchainProxy rentalBlockchainProxy;

    private CarService carService;

    private final String carName1 = "CarName1";
    private final String carName2 = "CarName2";
    private final Car completeCar1 = new Car(carName1);
    private final Car completeCar2 = new Car(carName2);
    private final BigInteger[] intArray = {BigInteger.valueOf(0),
                                           BigInteger.valueOf(1),
                                           BigInteger.valueOf(2)};
    private final Boolean[] booleanArray = {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE};
    private final List<BigInteger> intList = Lists.newArrayList(intArray);
    private final List<Boolean> booleanList = Lists.newArrayList(booleanArray);


    @BeforeEach
    void setUp() {
        completeCar1.setId(0);
        completeCar2.setId(1);
        when(carRepository.save(any(Car.class))).thenReturn(completeCar1);
        when(carRepository.getById(0)).thenReturn(completeCar1);
        when(carRepository.getById(1)).thenReturn(completeCar2);
    }

    @Test
    @Transactional
    void checkAddCarSuccess() throws Exception {
        when(rentalBlockchainProxy.addCar(any())).thenReturn(null);
        this.carService = new CarService(carRepository, rentalBlockchainProxy);
        try {
            final CarData car = carService.create(new CarInputData("Test"));
            assertThat(car.carName()).isEqualTo(carName1);
            assertThat(car.id()).isEqualTo(0);
        } catch (CarAlreadyExistsError e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void checkAddCarFailureBlockchainError() throws Exception {
        when(rentalBlockchainProxy.addCar(any())).thenThrow(new Exception());
        this.carService = new CarService(carRepository, rentalBlockchainProxy);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> carService.create(new CarInputData("Test1")));
    }

    @Test
    @Transactional
    void checkAddCarFailureCarAlreadyExists() throws Exception {
        when(rentalBlockchainProxy.addCar(any()))
                .thenThrow(new Exception("Car already exists!"));
        this.carService = new CarService(carRepository, rentalBlockchainProxy);
        assertThatExceptionOfType(CarAlreadyExistsError.class)
                .isThrownBy(() -> carService.create(new CarInputData("Test2")));
    }

    @Test
    @Transactional
    void getCarsSuccess() throws Exception {
        when(rentalBlockchainProxy.getAllAvailableCars())
                .thenReturn(new Tuple2<>(intList, booleanList));
        this.carService = new CarService(carRepository, rentalBlockchainProxy);
        final CarList list = carService.getCars();
        assertThat(list.cars().size()).isEqualTo(2);
        assertThat(list.cars().get(0).carName()).isEqualTo(carName1);
        assertThat(list.cars().get(1).carName()).isEqualTo(carName2);
    }

    @Test
    @Transactional
    void getCarsFailure() throws Exception {
        when(rentalBlockchainProxy.getAllAvailableCars()).thenThrow(new Exception());
        this.carService = new CarService(carRepository, rentalBlockchainProxy);
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> carService.getCars());
    }
}
