package com.example.usercrud.exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String message){
        super(message);
    }
}
