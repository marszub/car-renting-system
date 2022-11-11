package pl.agh.edu.cardatabase.car;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.edu.cardatabase.car.dto.CarData;
import pl.agh.edu.cardatabase.car.dto.CarInputData;
import pl.agh.edu.cardatabase.car.dto.AdminCarList;
import pl.agh.edu.cardatabase.car.dto.CarLocationUpdateInput;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.error.CarCategoryDoesNotExistError;
import pl.agh.edu.cardatabase.car.error.CarDoesNotExistError;
import pl.agh.edu.cardatabase.car.service.CarService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class AdminCarController {
    private final CarService carService;

    public AdminCarController(final CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CarData create(@Valid @RequestBody final CarInputData data) throws CarAlreadyExistsError,
            CarCategoryDoesNotExistError {
        return carService.create(data);
    }

    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    public AdminCarList adminGetAvailableCars() {
        return carService.adminGetCars();
    }

    @Transactional
    @PatchMapping("/cars/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCarLocation(@Valid @RequestBody final CarLocationUpdateInput data,
                                  @PathVariable Integer id) throws CarDoesNotExistError {
        carService.updateCarLocation(data, id);
    }
}
