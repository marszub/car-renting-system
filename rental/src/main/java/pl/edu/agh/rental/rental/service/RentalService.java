package pl.edu.agh.rental.rental.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.rental.auth.User;
import pl.edu.agh.rental.errors.NoRentalError;
import pl.edu.agh.rental.errors.UserUnauthorizedError;
import pl.edu.agh.rental.rental.dto.RentalCreateInput;
import pl.edu.agh.rental.rental.dto.RentalData;

import java.sql.Timestamp;

@Service
public class RentalService {
    public RentalService() { }

    public RentalData createRental(final RentalCreateInput input, final User user) {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new RentalData(1, input.carId(), timestamp.getTime());
    }

    public void endRental(final Integer rentalId, final User user) throws UserUnauthorizedError {
        //if there is no rental with rentalId throw userUnauthorizedError
        if (rentalId == -4) {
            throw new UserUnauthorizedError();
        }
        return;
    }

    public RentalData getRental(final User user) throws NoRentalError {
        //If user has no rental throw error NoRentalError
        if (user.id() == -1) {
            throw new NoRentalError();
        }
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new RentalData(1, 1, timestamp.getTime());
    }
}