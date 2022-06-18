package pl.agh.edu.carRentingMicroservice.reservation.service;

import org.springframework.stereotype.Service;
import pl.agh.edu.carRentingMicroservice.auth.User;
import pl.agh.edu.carRentingMicroservice.reservation.dto.ReservationCreateInput;
import pl.agh.edu.carRentingMicroservice.reservation.dto.ReservationData;

import java.sql.Timestamp;

@Service
public class ReservationService {
    public ReservationService() { }

    public ReservationData createReservation(final ReservationCreateInput input, final User user) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new ReservationData(1, input.carId(), timestamp.getTime());
    }

    public void endReservation(final Integer reservationId, final User user) {
        return;
    }

    public ReservationData getReservation(final User user) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new ReservationData(1, 1, timestamp.getTime());
    }
}
