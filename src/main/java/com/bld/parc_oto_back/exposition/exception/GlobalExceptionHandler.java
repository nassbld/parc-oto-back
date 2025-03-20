package com.bld.parc_oto_back.exposition.exception;

import com.bld.parc_oto_back.domain.exception.UserAlreadyExistsException;
import com.bld.parc_oto_back.domain.exception.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExists(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Une erreur de contrainte d'unicité s'est produite";

        String exceptionMessage = ex.getMostSpecificCause().getMessage();

        if (exceptionMessage.contains("UK_email")) {
            message = "Cette adresse email est déjà utilisée";
        } else if (exceptionMessage.contains("UK_matricule")) {
            message = "Ce matricule est déjà utilisé";
        } else if (exceptionMessage.contains("UK_phone")) {
            message = "Ce numéro de téléphone est déjà utilisé";
        }

        ErrorResponse error = new ErrorResponse(
                message,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
