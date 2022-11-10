package pl.agh.edu.cardatabase.car.dto;

import pl.agh.edu.cardatabase.car.persistence.Car;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryData;

import javax.validation.constraints.NotNull;

public record AdminCarData(
        @NotNull
        Integer id,

        @NotNull
        String carName,

        @NotNull
        CarCategoryData carCategory,

        @NotNull
        Coordinates coordinates,

        @NotNull
        CarStatus carStatus

){
    public AdminCarData(final Car car, CarStatus status){
            this(car.getId(), car.getName(), new CarCategoryData(car.getCarCategory()),
                    new Coordinates(car.getLatitude(), car.getLongitude()), status);
    }
}
