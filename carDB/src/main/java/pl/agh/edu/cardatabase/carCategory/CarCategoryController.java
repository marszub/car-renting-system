package pl.agh.edu.cardatabase.carCategory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryList;
import pl.agh.edu.cardatabase.carCategory.service.CarCategoryService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CarCategoryController {
    private final CarCategoryService carCategoryService;

    public CarCategoryController(final CarCategoryService carCategoryService) {
        this.carCategoryService = carCategoryService;
    }

    @GetMapping("/carCategories")
    @ResponseStatus(HttpStatus.OK)
    public CarCategoryList getAvailableCarCategories() {
        return carCategoryService.getCategories();
    }
}
