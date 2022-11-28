package pl.edu.agh.payment.payment;

import Rental.RentalDoesNotExistException;
import Rental.UnauthorizedRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.payment.payment.auth.CurrentUser;
import pl.edu.agh.payment.payment.auth.User;
import pl.edu.agh.payment.payment.dto.Redirect;
import pl.edu.agh.payment.payment.dto.PaymentInputData;
import pl.edu.agh.payment.payment.error.NotificationNotValidatedError;
import pl.edu.agh.payment.payment.service.PaymentService;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    public Redirect startPayment(@Valid @RequestBody final PaymentInputData data, @CurrentUser final User user)
            throws UnauthorizedRequestException, RentalDoesNotExistException {
        return paymentService.createPayment(data, user);
    }

    @PostMapping("/notification")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void receiveFromPayU(HttpEntity<String> httpEntity) throws NotificationNotValidatedError {
        paymentService.receiveNotification(httpEntity);
    }
}
