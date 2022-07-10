package pl.edu.agh.cardatabase.car.dto;

import pl.edu.agh.cardatabase.car.persistence.Car;

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
