package pl.agh.edu.cardatabase.car.dto;

import pl.agh.edu.cardatabase.car.persistence.Car;

import javax.validation.constraints.NotNull;

public record CarData(
        @NotNull
        Integer id,

        @NotNull
        String carName
) {
    public CarData(final Car car) {
        this(car.getId(), car.getName());
    }
}
