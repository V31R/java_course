package kalchenko.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidateExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){

        return methodArgumentNotValidException.getMessage();

    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> сonstraintViolationExceptionHandler(ConstraintViolationException сonstraintViolationException){

        return new ResponseEntity<>(сonstraintViolationException.getMessage(), HttpStatus.BAD_REQUEST);

    }
}
