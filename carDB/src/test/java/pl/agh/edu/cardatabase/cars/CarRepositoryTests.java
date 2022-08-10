package pl.agh.edu.cardatabase.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class CarRepositoryTests {
    @Autowired
    private CarRepository carRepository;

    private final String carName1 = "TestCar1";
    private final String carName2 = "TestCar2";

    @Test
    @Transactional
    void checkIfAfterSavingACarItIsAcquiredInDBRequest() {
        final Car car = new Car(carName1);
        carRepository.save(car);
        final List<Car> list = carRepository.getCars();
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getName()).isEqualTo(carName1);
        assertThat(list.get(0).getId()).isNotNull();
    }

    @Test
    @Transactional
    void checkIfSavingMultipleCarsAllowsToGetAllOfThem() {
        final Car car1 = new Car(carName1);
        final Car car2 = new Car(carName2);
        List<Car> list = carRepository.getCars();
        assertThat(list.size()).isEqualTo(0);
        carRepository.save(car1);
        carRepository.save(car2);
        list = carRepository.getCars();
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getId()).isNotNull();
        assertThat(list.get(1).getId()).isNotNull();
        assertThat(list.get(0).getId()).isNotEqualTo(list.get(1).getId());
    }
}
