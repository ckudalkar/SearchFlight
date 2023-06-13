package com.myprojects.springboot.exception;

/**
 *
 * FlightInfoNotFoundException
 *
 */
public class FlightInfoNotFoundException extends RuntimeException{

    /**
     * Traps customised message for why Flight Information was Not Found
     * @param message : customised exception message
     */
    public FlightInfoNotFoundException(String message){
        super(message);
    }
}
