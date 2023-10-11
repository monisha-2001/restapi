package com.rest.app;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(String message){
        super(message);
    }
}
