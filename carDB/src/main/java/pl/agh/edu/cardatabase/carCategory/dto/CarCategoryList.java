package pl.agh.edu.cardatabase.carCategory.dto;

import java.util.List;

public record CarCategoryList(
        List<CarCategoryData> carCategories
) {
    public CarCategoryList(final List<CarCategoryData> carCategories) {
        this.carCategories = carCategories;
    }
}
