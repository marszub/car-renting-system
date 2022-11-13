package pl.agh.edu.cardatabase;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import pl.agh.edu.cardatabase.car.persistence.CarRepository;
import pl.agh.edu.cardatabase.carCategory.ice.CarLocator;

@SpringBootApplication
public class CarDatabaseApplicationMain {
    final
    CarRepository carRepository;

    public CarDatabaseApplicationMain(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public static void main(final String[] args) {
        SpringApplication.run(CarDatabaseApplicationMain.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            try
            {
                Communicator communicator = Util.initialize(new String[]{}, environment.getProperty("ice.cardb.config-file"));
                var adapter = communicator.createObjectAdapter("Adapter");

                adapter.addServantLocator(new CarLocator(carRepository), "car");

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
