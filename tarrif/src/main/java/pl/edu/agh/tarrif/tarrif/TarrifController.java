package pl.edu.agh.tarrif.tarrif;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tarrif.auth.CurrentUser;
import pl.edu.agh.tarrif.auth.User;
import pl.edu.agh.tarrif.errors.UserUnauthorizedError;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrif.dto.RentalData;
import pl.edu.agh.tarrif.tarrif.service.TarrifService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pricing")
public class TarrifController {
    private final TarrifService tarrifService;

    public TarrifController(final TarrifService tarrifService) {
        this.tarrifService = tarrifService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<PricingRecord> getRental(@CurrentUser final User user){
        return tarrifService.getPricing();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addPricing(@Valid @RequestBody final PricingRecord data, @CurrentUser final User user) {
        return tarrifService.addPricing(data);
    }

}
