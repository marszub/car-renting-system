package pl.edu.agh.carManager.carManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.carManager.carManager.dto.CarInput;
import pl.edu.agh.carManager.carManager.error.DatabaseConflictError;
import pl.edu.agh.carManager.carManager.service.CarManagerService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/cars")
public class CarManagerController {
    private final CarManagerService carManagerService;

    public CarManagerController(final CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createCar(@Valid @RequestBody final CarInput data) throws DatabaseConflictError {
        carManagerService.createCar(data);
    }
}
