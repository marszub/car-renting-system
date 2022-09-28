package pl.edu.agh.carManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagerMain {
    public static String[] getArgs() {
        return args;
    }

    private static String[] args;

    public static void main(final String[] args) {
        ManagerMain.args = args;
        SpringApplication.run(ManagerMain.class, args);
    }
}
