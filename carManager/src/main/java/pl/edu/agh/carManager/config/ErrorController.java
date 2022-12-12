package pl.edu.agh.carManager.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.edu.agh.carManager.carManager.error.DatabaseConflictError;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DatabaseConflictError.class})
    public ResponseEntity<TextResponseDto> databaseConflictError() {
        String message = "Car with id already exists, or the token has been already taken";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new TextResponseDto(message));
    }

}
