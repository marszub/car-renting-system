package pl.agh.edu.cardatabase.car.dto;

import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryData;

import javax.validation.constraints.NotNull;

public record CarData(
        @NotNull
        Integer id,

        @NotNull
        String carName,

        @NotNull
        CarCategoryData carCategory,

        @NotNull
        Coordinates coordinates
) {
    public CarData(final Car car) {
        this(car.getId(), car.getName(), new CarCategoryData(car.getCarCategory()),
             new Coordinates(car.getLatitude(), car.getLongitude()));
    }
}
