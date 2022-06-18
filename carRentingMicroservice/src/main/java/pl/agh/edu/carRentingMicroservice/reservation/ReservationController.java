package pl.agh.edu.carRentingMicroservice.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.carRentingMicroservice.auth.CurrentUser;
import pl.agh.edu.carRentingMicroservice.auth.User;
import pl.agh.edu.carRentingMicroservice.reservation.dto.ReservationCreateInput;
import pl.agh.edu.carRentingMicroservice.reservation.dto.ReservationData;
import pl.agh.edu.carRentingMicroservice.reservation.service.ReservationService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationData createReservation(final ReservationCreateInput data, @CurrentUser User user) {
        return reservationService.createReservation(data, user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void endReservation(@PathVariable("id") final Integer reservationId, @CurrentUser final User user) {
        reservationService.endReservation(reservationId, user);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ReservationData getReservation(@CurrentUser User user) {
        return reservationService.getReservation(user);
    }
}
