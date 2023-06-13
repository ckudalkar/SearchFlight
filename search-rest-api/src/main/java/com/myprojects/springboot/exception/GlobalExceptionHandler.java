package com.myprojects.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 *
 * GlobalExceptionHandler : This is a Global Exception Handler used to handle all exceptions
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for FlightInfoNotFoundException.
     *
     * @param ex      : custom exception for Flight Information not found
     * @param wr : WebRequest interceptors, giving access to general request for which exception happened
     *                .
     * @return ErrorDetails
     */
    @ExceptionHandler(FlightInfoNotFoundException.class)
    public ResponseEntity<ErrorDetails>handleFlightInfoNotFoundException(FlightInfoNotFoundException ex,
                                                                         WebRequest wr){
        ErrorDetails err = new ErrorDetails(new Date(),
                                            ex.getMessage(),
                                            wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for Any other Global Exceptions.
     *
     * @param ex      : custom exception for Any other unanticipated global exceptions
     * @param wr : WebRequest interceptors, giving access to general request for which exception happened
     *                .
     * @return ErrorDetails
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails>handleGlobalException(Exception ex,
                                                                         WebRequest wr){
        ErrorDetails err = new ErrorDetails(new Date(),
                ex.getMessage(),
                wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
