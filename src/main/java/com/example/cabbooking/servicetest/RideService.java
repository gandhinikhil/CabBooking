package com.example.cabbooking.servicetest;

import com.example.cabbooking.model.Driver;
import com.example.cabbooking.model.User;
import com.example.cabbooking.vo.LocationDetailsVO;
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

    public List<Driver> findRide(String username, LocationDetailsVO source, LocationDetailsVO destination) {
        User user = userService.getUser(username);
        System.out.println(user.getName());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return driverService.findAvailableDrivers(source, 5); // Max distance of 5 units
    }

    public void chooseRide(String username, String driverName) {
        Driver driver = driverService.getDriver(driverName);
        if (driver != null && driver.isAvailable()) {
            driver.setAvailable(false); // Mark the driver as not available
        } else {
            throw new IllegalArgumentException("Driver not found or not available");
        }
    }


}
