package pl.edu.agh.rental.rental;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.rental.auth.CurrentUser;
import pl.edu.agh.rental.auth.User;
import pl.edu.agh.rental.errors.ActiveRentalError;
import pl.edu.agh.rental.errors.NoCarError;
import pl.edu.agh.rental.errors.NoRentalError;
import pl.edu.agh.rental.errors.UserUnauthorizedError;
import pl.edu.agh.rental.rental.dto.RentalCreateInput;
import pl.edu.agh.rental.rental.dto.RentalData;
import pl.edu.agh.rental.rental.service.RentalService;

import javax.validation.Valid;
import java.sql.Timestamp;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/rental")
public class RentalController {
    private final RentalService reservationService;

    public RentalController(final RentalService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RentalData createRental(@Valid @RequestBody final RentalCreateInput data, @CurrentUser final User user) throws NoCarError, ActiveRentalError {
        return reservationService.createRental(data.carId(), user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void endRental(@PathVariable("id") final Integer rentalId,
                          @CurrentUser final User user) throws UserUnauthorizedError {
        reservationService.endRental(rentalId, user);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public RentalData getRental(@CurrentUser final User user) throws NoRentalError {
        return reservationService.getRental(user);
    }

    //current cost - current rental
    @GetMapping("/cost/current")
    @ResponseStatus(HttpStatus.OK)
    public long getCurrentRentalCost(@CurrentUser final User user) throws NoRentalError {
        return reservationService.getCurrentRentalCost(user);
    }

    //total cost after end
    @GetMapping("/cost/{id}")
    @ResponseStatus(HttpStatus.OK)
    public long getRentalCost(@PathVariable("id") final Integer rentalId) throws UserUnauthorizedError{
        return reservationService.getRentalCost(rentalId);
    }
}
