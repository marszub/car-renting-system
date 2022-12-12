package pl.edu.agh.carManager.carManager.dto;

import pl.edu.agh.carManager.carManager.persistence.Car;

public record CarData(
        Integer carId,
        String carToken
) {
    public CarData(Car car) {
        this(car.getId(), car.getToken());
    }
}
