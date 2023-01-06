package pl.agh.edu.cardatabase;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.Util;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.transaction.support.TransactionTemplate;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.error.CarCategoryDoesNotExistError;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.car.service.CarService;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryInputData;
import pl.agh.edu.cardatabase.carCategory.ice.CarLocator;
import pl.agh.edu.cardatabase.carCategory.ice.CarPositionUpdaterServant;
import pl.agh.edu.cardatabase.carCategory.service.CarCategoryService;

import java.util.Objects;

@SpringBootApplication
public class CarDatabaseApplicationMain {
    final CarRepository carRepository;
    static CarService carService;
    static CarCategoryService carCategoryService;
    static Environment environment;

    public CarDatabaseApplicationMain(CarRepository carRepository, CarService carService, CarCategoryService carCategoryService, Environment environment) {
        this.carRepository = carRepository;
        CarDatabaseApplicationMain.carService = carService;
        CarDatabaseApplicationMain.carCategoryService = carCategoryService;
        CarDatabaseApplicationMain.environment = environment;
    }

    public static void main(final String[] args) {
        SpringApplication.run(CarDatabaseApplicationMain.class, args);
        createCars();
    }

    @Bean
    ApplicationRunner applicationRunner(TransactionTemplate transactionTemplate) {
        return args -> {
            try
            {
                Communicator communicator = Util.initialize(new String[]{}, environment.getProperty("ice.cardb.config-file"));
                var adapter = communicator.createObjectAdapter("Adapter");

                adapter.addServantLocator(new CarLocator(carRepository), "car");
                adapter.add(new CarPositionUpdaterServant(environment, carService, transactionTemplate), new Identity("positionUpdater", "positionUpdater"));

                adapter.activate();
                //communicator.waitForShutdown();
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        };
    }

    static void createCars() {
        try {
            Thread.sleep(Long.parseLong(Objects.requireNonNull(environment.getProperty("generation.sleep_time"))));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        carCategoryService.create(new CarCategoryInputData("Car"));
        int carsNumber = Integer.parseInt(Objects.requireNonNull(environment.getProperty("generation.car_number")));
        for(int i = 0; i < carsNumber; i++) {
            System.out.println("CAR_" + i);
            try {
                carService.create(new CarInputData( "Car" + String.valueOf(i), 1));
            } catch (CarAlreadyExistsError | CarCategoryDoesNotExistError e) {
                e.printStackTrace();
            }
        }
    }
}
