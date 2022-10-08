package pl.agh.edu.cardatabase.carCategory.dto;

import pl.agh.edu.cardatabase.carCategory.persistence.CarCategory;

import javax.validation.constraints.NotNull;

public record CarCategoryData(
        @NotNull
        Integer id,

        @NotNull
        String carCategoryName
) {
    public CarCategoryData(final CarCategory carCategory) {
        this(carCategory.getId(), carCategory.getName());
    }
}
