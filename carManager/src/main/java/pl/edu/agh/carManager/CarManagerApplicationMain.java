package pl.edu.agh.carManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import pl.edu.agh.carManager.carManager.persistence.CarRepository;
import pl.edu.agh.carManager.carManager.service.CarsMap;
import pl.edu.agh.carManager.carManager.service.ConnectionService;

@SpringBootApplication
public class CarManagerApplicationMain {

    public CarManagerApplicationMain(final ConnectionService connectionService) {
        connectionService.start();
    }

    public static void main(final String[] args) {
        SpringApplication.run(CarManagerApplicationMain.class, args);
    }

}
