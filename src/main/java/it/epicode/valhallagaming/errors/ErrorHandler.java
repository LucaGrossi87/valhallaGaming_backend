package it.epicode.valhallagaming.errors;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException error){
        String strError = error.getMessage();
        return new ResponseEntity<>(strError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntityExists(EntityExistsException error){
        String strError = error.getMessage();
        return new ResponseEntity<>(strError, HttpStatus.FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException error) {
        String strError = error.getMessage();
        return new ResponseEntity<>(strError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException error){
        Map<String, String> errorResponse = new HashMap<>();
        error.getBindingResult().getAllErrors().forEach(
                er->{
                    FieldError frError = (FieldError) er;
                    errorResponse.put(frError.getField(), frError.getDefaultMessage());
                });
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception error) {
        String strError = error.getMessage();
        return new ResponseEntity<>(strError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
