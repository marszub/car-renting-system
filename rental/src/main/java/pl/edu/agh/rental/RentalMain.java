package pl.edu.agh.rental;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import pl.edu.agh.rental.rental.ice.RentalLocator;
import pl.edu.agh.rental.rental.service.RentalService;

@SpringBootApplication
public class RentalMain {
    public static String[] getArgs() {
        return args;
    }

    private final RentalService rentalService;

    public RentalMain(final RentalService rentalService) {
        this.rentalService = rentalService;
    }

    private static String[] args;

    public static void main(final String[] args) {
        RentalMain.args = args;
        SpringApplication.run(RentalMain.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            try
            {
                Communicator communicator = Util.initialize(new String[]{}, environment.getProperty("ice.rental.config-file"));
                var adapter = communicator.createObjectAdapter("Adapter");

                adapter.addServantLocator(new RentalLocator(rentalService, environment), "rental");

                adapter.activate();
            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        };
    }
}
