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
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.car.service.CarService;
import pl.agh.edu.cardatabase.carCategory.ice.CarLocator;
import pl.agh.edu.cardatabase.carCategory.ice.CarPositionUpdaterServant;

@SpringBootApplication
public class CarDatabaseApplicationMain {
    final CarRepository carRepository;
    final CarService carService;

    public CarDatabaseApplicationMain(CarRepository carRepository, CarService carService) {
        this.carRepository = carRepository;
        this.carService = carService;
    }

    public static void main(final String[] args) {
        SpringApplication.run(CarDatabaseApplicationMain.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment, TransactionTemplate transactionTemplate) {
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
}
