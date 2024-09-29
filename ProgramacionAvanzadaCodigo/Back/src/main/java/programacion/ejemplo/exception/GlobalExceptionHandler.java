package programacion.ejemplo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import programacion.ejemplo.exception.EntidadDuplicadaException;
import programacion.ejemplo.exception.EntidadFormatoInvalidoException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja todas las RuntimeExceptions (incluyendo tus excepciones personalizadas)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Maneja específicamente EntidadDuplicadaException
    @ExceptionHandler(EntidadDuplicadaException.class)
    public ResponseEntity<String> handleEntidadDuplicadaException(EntidadDuplicadaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Maneja específicamente EntidadFormatoInvalidoException
    @ExceptionHandler(EntidadFormatoInvalidoException.class)
    public ResponseEntity<String> handleEntidadFormatoInvalidoException(EntidadFormatoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
