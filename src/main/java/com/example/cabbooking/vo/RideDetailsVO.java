package com.example.cabbooking.vo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RideDetailsVO {
    private DriverDetailsVO driver;
    private double fare;

}
