package pl.agh.edu.cardatabase.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.agh.edu.cardatabase.car.error.CarAlreadyExistsError;
import pl.agh.edu.cardatabase.car.error.CarCategoryDoesNotExistError;
import pl.agh.edu.cardatabase.car.error.CarDoesNotExistError;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CarAlreadyExistsError.class})
    public ResponseEntity<TextResponseDto> handleCarAlreadyExistsError() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(new TextResponseDto("Car already exists"));
    }

    @ExceptionHandler({CarCategoryDoesNotExistError.class})
    public ResponseEntity<TextResponseDto> handleCategoryDoesNotExistError() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(new TextResponseDto("Given category does not exist"));
    }

    @ExceptionHandler({CarDoesNotExistError.class})
    public ResponseEntity<TextResponseDto> CarDoesNotExistError() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new TextResponseDto("Car with a given ID does not exist"));
    }
}
