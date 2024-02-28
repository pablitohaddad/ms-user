package io.github.pablitohaddad.msuser.exceptions.handler;

import io.github.pablitohaddad.msuser.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(UniqueViolationException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(UserNotFoundException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }
    @ExceptionHandler({PasswordInvalidException.class})
    public ResponseEntity<ErrorMessage> handlePasswordInvalidException(UserNotFoundException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
    @ExceptionHandler({PostalCodeNotFoundException.class})
    public ResponseEntity<ErrorMessage> handlePostalCodeNotFoundException(PostalCodeNotFoundException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }
    @ExceptionHandler({UnauthorizedJwtTokenException.class})
    public ResponseEntity<ErrorMessage> handleJwtTokenUnauthorizedException(UnauthorizedJwtTokenException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }
    @ExceptionHandler({UpdateFailedException.class})
    public ResponseEntity<ErrorMessage> handleUpdatedFailedException(UpdateFailedException ex, HttpServletRequest request){
        log.error("API ERROR: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}
