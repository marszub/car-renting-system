package pl.agh.edu.cardatabase.car.dto;

import java.util.List;

public record CarList(
        List<CarData> cars
) {
}
