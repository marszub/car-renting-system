package pl.edu.agh.payment.payment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.payment.payment.dto.InputNotification;
import pl.edu.agh.payment.payment.dto.PaymentList;
import pl.edu.agh.payment.payment.service.PaymentService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/payment")
public class AdminPaymentController {
    private final PaymentService paymentService;

    public AdminPaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PaymentList getPayments() {
        return paymentService.getPayments();
    }

    //That endpoint will be deleted it servers only as a mock of a PayU notification
    @GetMapping("sendNotification")
    @ResponseStatus(HttpStatus.OK)
    public void sendNotification(@RequestBody @Valid final InputNotification inputNotification) {
        paymentService.sendNotification(inputNotification);
    }
}
