package com.example.cabbooking.servicetest;

import com.example.cabbooking.constant.FareConstant;
import com.example.cabbooking.model.Driver;
import com.example.cabbooking.model.User;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.RideDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {
    @Autowired
    private final UserService userService;
    @Autowired
    private final DriverService driverService;

    public RideService(UserService userService, DriverService driverService) {
        this.userService = userService;
        this.driverService = driverService;
    }

    public List<RideDetailsVO> findRide(String username, LocationDetailsVO source, LocationDetailsVO destination) {
        User user = userService.getUser(username);
        System.out.println(user.getName());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return driverService.findAvailableDrivers(source,destination,5); // Max distance of 5 units
    }

    public void  chooseRide(String username, String driverName) {
        Driver driver = driverService.getDriver(driverName);
        if (driver != null && driver.isAvailable()) {

            driver.setAvailable(false); // Mark the driver as not available
        } else {
            throw new IllegalArgumentException("Driver not found or not available");
        }
    }
    public double calculateFare(LocationDetailsVO source,LocationDetailsVO destination){
        double distance  = calculateDistance(source,destination);
        double distanceFare = distance * FareConstant.PER_KM_RATE;
        double taxAmount =  distanceFare * FareConstant.TAX_RATE;

        return FareConstant.BASE_FARE + distanceFare + taxAmount + FareConstant.PLATFORM_CHARGE;

    }

    private double calculateDistance(LocationDetailsVO source, LocationDetailsVO destination) {
        double xDiff = destination.getX() - source.getX();
        double yDiff = destination.getY()-source.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }


}
