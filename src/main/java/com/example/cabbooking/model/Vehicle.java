package com.example.cabbooking.model;

public class Vehicle {
    private String model;
    private String registrationNumber;

    public Vehicle(String model, String registrationNumber) {
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}