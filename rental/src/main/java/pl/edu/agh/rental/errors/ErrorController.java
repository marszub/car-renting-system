package pl.edu.agh.rental.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserUnauthorizedError.class})
    public ResponseEntity<ErrorResponseDto> handleUserUnauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDto("User unauthorized"));
    }

    @ExceptionHandler({NoRentalError.class})
    public ResponseEntity<Void> handleNoRentalError() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
