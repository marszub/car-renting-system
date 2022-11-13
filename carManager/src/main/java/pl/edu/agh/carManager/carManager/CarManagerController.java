package pl.edu.agh.carManager.carManager;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.carManager.carManager.dto.CarData;
import pl.edu.agh.carManager.carManager.dto.PricingRecord;
import pl.edu.agh.carManager.carManager.enums.CarStatus;
import pl.edu.agh.carManager.carManager.service.CarManagerService;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CarManagerController {
    private final CarManagerService carManagerService;

    public CarManagerController(final CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @GetMapping("/carActivity/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarStatus carActivity(@PathVariable("id") int car_id) throws Exception {
        return carManagerService.carActivity(car_id);
    }//TODO change
    //enum in blockchain -
    //Active, Parked, Non-Active, Service

    @PostMapping("/addCar")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCar(@Valid @RequestBody final CarData data) {
        carManagerService.addCar(data); // TODO change to ICE
    }
    //called together with car DB, creates a car

    @DeleteMapping("/deleteCar/:id")
    @ResponseStatus(HttpStatus.OK)
    public void removeCar(@PathVariable("id") int car_id){
        carManagerService.removeCar(car_id);
    }//TODO change to ICE
    //removes car, is it a good idea?

}
