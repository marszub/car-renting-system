package pl.agh.edu.cardatabase.car.dto;

import java.util.List;

public record AdminCarList(
        List<AdminCarData> cars
) {
}
