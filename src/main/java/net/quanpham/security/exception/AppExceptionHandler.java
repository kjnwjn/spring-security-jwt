package net.quanpham.security.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {

        ErrorResponse err = new ErrorResponse(500, e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(500).body(err);
    }
}
