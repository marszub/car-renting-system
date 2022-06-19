package pl.edu.agh.rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RentalMain {
    public static String[] getArgs() {
        return args;
    }

    private static String[] args;

    public static void main(final String[] args) {
        RentalMain.args = args;
        SpringApplication.run(RentalMain.class, args);
    }
}
