package com.example.cabbooking.customExecption;

import org.checkerframework.checker.units.qual.A;

public class AlreadyCanceledException extends RuntimeException{
    public AlreadyCanceledException(String message){
        super(message);
    }
}
