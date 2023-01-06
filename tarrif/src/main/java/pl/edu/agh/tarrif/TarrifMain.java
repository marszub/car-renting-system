package pl.edu.agh.tarrif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrif.service.TarrifService;

import java.util.Objects;

@SpringBootApplication
public class TarrifMain {
    private static TarrifService tarrifService;
    private static Environment environment;

    public TarrifMain(final TarrifService tarrifService, Environment environment) {
        TarrifMain.tarrifService = tarrifService;
        TarrifMain.environment = environment;
    }
    public static String[] getArgs() {
        return args;
    }

    private static String[] args;

    public static void main(final String[] args) {
        TarrifMain.args = args;
        SpringApplication.run(TarrifMain.class, args);
        addTarrif();
    }

    private static void addTarrif() {
        try {
            Thread.sleep(Long.parseLong(Objects.requireNonNull(environment.getProperty("generation.sleep_time"))));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ADDING");
        tarrifService.addPricing(new PricingRecord(1, 100));
    }
}
