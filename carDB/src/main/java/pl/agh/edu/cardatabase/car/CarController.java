package pl.agh.edu.cardatabase.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.dto.CarData;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.dto.CarList;
import pl.agh.edu.cardatabase.car.error.CarCategoryDoesNotExistError;
import pl.agh.edu.cardatabase.car.service.CarService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CarController {
    private final CarService carService;

    public CarController(final CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/admin/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarData create(@Valid @RequestBody final CarInputData data) throws CarAlreadyExistsError, CarCategoryDoesNotExistError {
        return carService.create(data);
    }

    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    public CarList getAvailableCars() {
        return carService.getCars();
    }

}
