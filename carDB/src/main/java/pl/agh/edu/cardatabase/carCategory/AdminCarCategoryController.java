package pl.agh.edu.cardatabase.carCategory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryData;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryInputData;
import pl.agh.edu.cardatabase.carCategory.dto.CarCategoryList;
import pl.agh.edu.cardatabase.carCategory.service.CarCategoryService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class AdminCarCategoryController {
    private final CarCategoryService carCategoryService;

    public AdminCarCategoryController(final CarCategoryService carCategoryService) {
        this.carCategoryService = carCategoryService;
    }

    @PostMapping("/carCategories")
    @ResponseStatus(HttpStatus.CREATED)
    public CarCategoryData createCarCategory(@Valid @RequestBody final CarCategoryInputData data) {
        return carCategoryService.create(data);
    }

    @GetMapping("/carCategories")
    @ResponseStatus(HttpStatus.OK)
    public CarCategoryList getAvailableCarCategories() {
        return carCategoryService.getCategories();
    }

}
