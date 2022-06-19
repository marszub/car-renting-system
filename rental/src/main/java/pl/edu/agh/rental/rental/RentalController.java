package pl.edu.agh.rental.rental;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.rental.auth.CurrentUser;
import pl.edu.agh.rental.auth.User;
import pl.edu.agh.rental.errors.NoRentalError;
import pl.edu.agh.rental.errors.UserUnauthorizedError;
import pl.edu.agh.rental.rental.dto.RentalCreateInput;
import pl.edu.agh.rental.rental.dto.RentalData;
import pl.edu.agh.rental.rental.service.RentalService;

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
    public RentalData createRental(final RentalCreateInput data, @CurrentUser final User user) {
        return reservationService.createRental(data, user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void endRental(@PathVariable("id") final Integer reservationId,
                          @CurrentUser final User user) throws UserUnauthorizedError {
        reservationService.endRental(reservationId, user);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public RentalData getRental(@CurrentUser final User user) throws NoRentalError {
        return reservationService.getRental(user);
    }
}
