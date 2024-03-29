package pl.edu.agh.tarrif.tarrif;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecord;
import pl.edu.agh.tarrif.tarrif.dto.PricingRecordsList;
import pl.edu.agh.tarrif.tarrif.service.TarrifService;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TarrifController {
    private final TarrifService tarrifService;

    public TarrifController(final TarrifService tarrifService) {
        this.tarrifService = tarrifService;
    }

    @GetMapping("/pricing")
    @ResponseStatus(HttpStatus.OK)
    public PricingRecordsList getPricing(){
        return tarrifService.getPricing();
    }
}
