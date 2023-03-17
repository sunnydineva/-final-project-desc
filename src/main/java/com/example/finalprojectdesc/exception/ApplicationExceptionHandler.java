package com.example.finalprojectdesc.exception;


import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;


@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    String handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        if (ex.getErrorCode() == 23505) {
            // handle duplicate key violation
            return "Duplicate key error: " + ex.getMessage();
        } else if (ex.getErrorCode() == 23503) {
            // handle foreign key violation
            return "Record not found error: " + ex.getMessage();
        } else if (ex.getErrorCode() == 31932) {
            return "Duplicates for email not allowed" + ex.getMessage();
        } else if (ex.getErrorCode() == 1451) {
            //handle delete or update parent row
            return """
                    Cannot delete the record because it is referenced by another records in the database.\040
                    Please delete the corresponding records first before attempting to delete this record""";
        } else {
            // handle other types of constraint violation errors
            return "SQL constraint violation error: " + ex.getMessage();
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND) //get not existing record
    @ExceptionHandler(EmptyResultDataAccessException.class)
    String handlerEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return "No result to be shown";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND) //get not existing record
    @ExceptionHandler(RecordNotFoundException.class)
    String handlerRecordNotFoundException(RecordNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleConstraintViolationException(ConstraintViolationException e) {
        return "not valid due to validation error: " + e.getMessage();
    }

    /* exceptionHandler for controller validation errors */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException e) {
        String errorMessage = "Request not valid due to validation error."; //if not defined with the validation
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            if (error.getField().equals("password") && error.getCode().equals("Pattern")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("newPassword") && error.getCode().equals("Pattern")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("email") && error.getCode().equals("Email")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("id") && error.getCode().equals("NotNull")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("seats") && error.getCode().equals("NotNull")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("pricePerDay") && error.getCode().equals("NotNull")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("startDate") && error.getCode().equals("NotNull")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("endDate") && error.getCode().equals("NotNull")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("currentPricePerDay") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("newPricePerDay") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("make") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("model") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("color") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("carPlate") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("firstName") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("LastName") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("phone") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("email") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("currentPassword") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("newPassword") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("newPasswordConfirm") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("pickUpDate") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("returnDate") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("currentPickUpDate") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("currentReturnDate") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("newPickUpDate") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("newReturnDate") && error.getCode().equals("NotBlank")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("firstName") && error.getCode().equals("Size")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("LastName") && error.getCode().equals("Size")) {
                errorMessage = error.getDefaultMessage();
                break;
            } else if (error.getField().equals("phone") && error.getCode().equals("Size")) {
                errorMessage = error.getDefaultMessage();
                break;
            }

        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}

