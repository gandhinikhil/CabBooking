package com.example.cabbooking.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Driver {
    private String name;
    private String gender;
    private int age;
    private Vehicle vehicle;
    private Location currentLocation;
    private boolean isAvailable;


}