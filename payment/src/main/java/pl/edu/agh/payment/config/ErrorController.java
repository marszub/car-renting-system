package pl.edu.agh.payment.config;

import Rental.RentalDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.edu.agh.payment.payment.error.NotificationNotValidatedError;
import Rental.UnauthorizedRequestException;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotificationNotValidatedError.class})
    public ResponseEntity<TextResponseDto> NotificationNotValidatedError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new TextResponseDto("Validation Error"));
    }

    @ExceptionHandler({RentalDoesNotExistException.class})
    public ResponseEntity<TextResponseDto> RentalDoesNotExist() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new TextResponseDto("Rental does not exist"));
    }

    @ExceptionHandler({UnauthorizedRequestException.class})
    public ResponseEntity<TextResponseDto> UnauthorizedRequest() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new TextResponseDto("Unknown error"));
    }
}
