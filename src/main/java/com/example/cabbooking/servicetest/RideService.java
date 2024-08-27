package com.example.cabbooking.servicetest;

import com.example.cabbooking.constant.FareConstant;
import com.example.cabbooking.vo.DriverDetailsVO;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.RideDetailsVO;
import com.example.cabbooking.vo.UserDetailsInVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
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
        UserDetailsInVO user = userService.getUser(username);
        System.out.println(user.getUserName());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return driverService.findAvailableDrivers(source,destination,5); // Max distance of 5 units
    }

    public void  chooseRide(String username, String driverName) {
        DriverDetailsVO driver = driverService.getDriver(driverName);
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
        double xDiff = destination.getXDistance() - source.getXDistance();
        double yDiff = destination.getYDistance()-source.getYDistance();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
    public void sortedDriversByDistanceToSource(LocationDetailsVO sources, List<RideDetailsVO>driverDetails){
        Collections.sort(driverDetails, new Comparator<RideDetailsVO>() {
            @Override
            public int compare(RideDetailsVO o1, RideDetailsVO o2) {
                double distanceToO1 = calculateDistance(sources,o1.getDriver().getLocation());
                double distanceToO2 = calculateDistance(sources,o2.getDriver().getLocation());
                return Double.compare(distanceToO1,distanceToO2);
            }
        });
    }

}
