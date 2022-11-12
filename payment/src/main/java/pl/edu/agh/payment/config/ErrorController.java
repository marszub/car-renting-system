package pl.edu.agh.payment.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.edu.agh.payment.payment.error.NotificationNotValidatedError;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotificationNotValidatedError.class})
    public ResponseEntity<TextResponseDto> NotificationNotValidatedError() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new TextResponseDto("Validation Error"));
    }
}
