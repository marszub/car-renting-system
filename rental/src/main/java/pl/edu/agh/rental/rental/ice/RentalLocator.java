package pl.edu.agh.rental.rental.ice;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.ServantLocator;
import com.zeroc.Ice.UserException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.edu.agh.rental.rental.service.RentalService;

@Component
public class RentalLocator implements ServantLocator {
    private final RentalService rentalService;
    private final Environment environment;

    public RentalLocator(final RentalService rentalService, final Environment environment) {
        this.rentalService = rentalService;
        this.environment = environment;
    }

    @Override
    public LocateResult locate(Current curr) throws UserException {
        System.out.println("Locating: " + curr.id.category + " " + curr.id.name);
        return new LocateResult(new RentalServant(rentalService, environment), new Object());
    }

    @Override
    public void deactivate(String category) {
    }

    @Override
    public void finished(Current curr, com.zeroc.Ice.Object servant, Object cookie) throws UserException {
    }
}
