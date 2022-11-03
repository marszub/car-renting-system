package pl.edu.agh.tarrif.tarrif;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.tarrif.auth.CurrentUser;
import pl.edu.agh.tarrif.auth.User;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecordsList;
import pl.edu.agh.tarrif.tarrif.service.TarrifService;
import javax.validation.Valid;
import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TarrifController {
    private final TarrifService tarrifService;

    public TarrifController(final TarrifService tarrifService) {
        this.tarrifService = tarrifService;
    }

    @GetMapping("/admin/pricing")
    @ResponseStatus(HttpStatus.OK)
    public PricingRecordsList getPricing(){
        return tarrifService.getPricing();
    }

    @PostMapping("/admin/pricing")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean addPricing(@Valid @RequestBody final PricingRecord data) {
        return tarrifService.addPricing(data);
    }

}
