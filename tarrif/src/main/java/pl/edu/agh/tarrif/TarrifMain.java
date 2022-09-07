package pl.edu.agh.tarrif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TarrifMain {
    public static String[] getArgs() {
        return args;
    }

    private static String[] args;

    public static void main(final String[] args) {
        TarrifMain.args = args;
        SpringApplication.run(TarrifMain.class, args);
    }
}
