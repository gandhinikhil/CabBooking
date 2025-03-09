package com.example.cabbooking.customExecption;

public class UserDetailNotFoundException extends RuntimeException {
     public UserDetailNotFoundException(String message){
         super(message);
     }

}
