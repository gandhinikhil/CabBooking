package com.example.cabbooking.customExecption;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(String message){
        super(message);
    }
}
