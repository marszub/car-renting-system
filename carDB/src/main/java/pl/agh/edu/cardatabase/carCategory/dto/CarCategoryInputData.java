package pl.agh.edu.cardatabase.carCategory.dto;

import javax.validation.constraints.NotNull;

public record CarCategoryInputData(
        @NotNull
        String categoryName
) { }
