package pl.edu.agh.carManager.carManager;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.carManager.carManager.dto.PricingRecord;
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
    public CarActivity carActivity(@PathVariable("id") int car_id){
        return carManagerService.carActivity(car_id);
    }//TODO change
    //enum in blockchain -
    //Active, Parked, Non-Active, Przegląd

    @PostMapping("/addCar")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addCar(@Valid @RequestBody final PricingRecord data) {
        return carManagerService.addPricing(data); // TODO change
    }
    //called together with car DB, creates a car

    @DeleteMapping("/deleteCar/:id")
    @ResponseStatus(HttpStatus.OK)
    public void removeCar(){return;}//TODO change
    //removes car, is it a good idea?

}
