package it.euris.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
@Data
@AllArgsConstructor
public class GlobalExceptionHandler {

	 @ExceptionHandler(EntityNotFoundException.class)
	    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	    
	    @ExceptionHandler(InsufficientStockException.class)
	    public ResponseEntity<String> handleInsufficientStock(InsufficientStockException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }
	    
	    @ExceptionHandler(IllegalStateException.class)
	    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	    }

}
