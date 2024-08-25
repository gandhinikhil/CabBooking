package com.example.cabbooking.servicetest;

import com.example.cabbooking.vo.DriverDetailsVO;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.RideDetailsVO;
import com.example.cabbooking.vo.VehicleDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverService {
    private final Map<String, DriverDetailsVO> drivers = new HashMap<>();
    @Autowired
    @Lazy
    private RideService rideService;

    public void addDriver(DriverDetailsVO driverDetailsVO) {
        VehicleDetailsVO vehicleDetailsVO = driverDetailsVO.getVehicle();
        LocationDetailsVO locationDetailsVO = driverDetailsVO.getLocation();
        VehicleDetailsVO vehicle = new VehicleDetailsVO(vehicleDetailsVO.getModel(), vehicleDetailsVO.getRegistrationNumber());
        LocationDetailsVO location = new LocationDetailsVO(locationDetailsVO.getX(), locationDetailsVO.getY());
        DriverDetailsVO driver = new DriverDetailsVO(driverDetailsVO.getName(), driverDetailsVO.getGender(), driverDetailsVO.getAge(), vehicle, location, true);
        drivers.put(driver.getName(), driver);
        System.out.println(driver.getName()+" "+driver.getGender()+" "+driver.getAge()+" "+driver.isAvailable()+" "+driver.getLocation()+" "+driver.getVehicle());
    }

    public List<RideDetailsVO> findAvailableDrivers(LocationDetailsVO source,LocationDetailsVO destination, int maxDistance) {
        List<RideDetailsVO> availableDrivers = new ArrayList<>();
        for (DriverDetailsVO driver : drivers.values()) {
            if (driver.isAvailable() && driver.getLocation().distanceTo(source)<= maxDistance) {
                double fare = rideService.calculateFare(source,destination);
                availableDrivers.add(new RideDetailsVO(driver,Math.round(fare*100.0)/100.0));
                System.out.println(driver.getName());
            }
        }
        rideService.sortedDriversByDistanceToSource(source,availableDrivers);
        return availableDrivers;
    }

    public DriverDetailsVO getDriver(String driverName) {
        return drivers.get(driverName);
    }
}
