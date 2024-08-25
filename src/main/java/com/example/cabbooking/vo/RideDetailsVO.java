package com.example.cabbooking.vo;

import com.example.cabbooking.model.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RideDetailsVO {
    private Driver driver;
    private double fare;

}
