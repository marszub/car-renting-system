package pl.agh.edu.cardatabase.car.dto;

import pl.agh.edu.cardatabase.car.persistence.Car;

import javax.validation.constraints.NotNull;

public record CarData(
        @NotNull
        Integer id,

        @NotNull
        String carName,

        @NotNull
        Integer carCategory,

        @NotNull
        Coordinates coordinates
) {
    public CarData(final Car car) {
        this(car.getId(), car.getName(), car.getCarCategory().getId(), new Coordinates(car.getLatitude(), car.getLongitude()));
    }
}
