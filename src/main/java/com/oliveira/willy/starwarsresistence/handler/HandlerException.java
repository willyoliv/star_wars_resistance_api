package com.oliveira.willy.starwarsresistence.handler;

import com.oliveira.willy.starwarsresistence.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RebelNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerRebelNotFoundException(RebelNotFoundException rebelNotFoundException) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Not found Exception, Rebel not found!")
                .details(rebelNotFoundException.getMessage())
                .developerMessage(rebelNotFoundException.getClass().getName())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidReportException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidReportException(InvalidReportException invalidReportException) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid report Exception, Report invalid")
                .details(invalidReportException.getMessage())
                .developerMessage(invalidReportException.getClass().getName())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateItemsInventoryException.class)
    public ResponseEntity<ExceptionDetails> handlerDuplicateItemsInventoryException(DuplicateItemsInventoryException duplicateItemsInventoryException) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Duplicate items inventory Exception")
                .details(duplicateItemsInventoryException.getMessage())
                .developerMessage(duplicateItemsInventoryException.getClass().getName())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTradeException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidTradeException(InvalidTradeException invalidTradeException) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid trade Exception, Trade invalid")
                .details(invalidTradeException.getMessage())
                .developerMessage(invalidTradeException.getClass().getName())
                .build(), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(ValidationExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Bad Request Exception, Invalid Fields")
                .details("Check the field(s) erro")
                .developerMessage(exception.getClass().getName())
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(exceptionDetails, headers, status);
    }


}
