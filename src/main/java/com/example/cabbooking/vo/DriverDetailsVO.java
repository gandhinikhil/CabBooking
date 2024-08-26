package com.example.cabbooking.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverDetailsVO {
    private String name;
    private String gender;
    private int age;
    private VehicleDetailsVO vehicle;
    private LocationDetailsVO location;
    private boolean isAvailable;
}
