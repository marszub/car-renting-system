package pl.edu.agh.carManager;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.Util;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import pl.edu.agh.carManager.carManager.ice.CarCommandsServant;
import pl.edu.agh.carManager.carManager.persistence.CarRepository;
import pl.edu.agh.carManager.carManager.service.CarManagerService;
import pl.edu.agh.carManager.carManager.service.CarsMap;
import pl.edu.agh.carManager.carManager.service.ConnectionService;

@SpringBootApplication
public class CarManagerApplicationMain {
    private CarManagerService carManagerService;

    public CarManagerApplicationMain(final ConnectionService connectionService, final CarManagerService carManagerService) {
        connectionService.start();
        this.carManagerService = carManagerService;
    }

    public static void main(final String[] args) {
        SpringApplication.run(CarManagerApplicationMain.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            try
            {
                Communicator communicator = Util.initialize(new String[]{}, environment.getProperty("ice.carManager.config-file"));
                var adapter = communicator.createObjectAdapter("Adapter");

                adapter.add(new CarCommandsServant(carManagerService, environment), new Identity("carCommands", "carManager"));

                adapter.activate();
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        };
    }

}
