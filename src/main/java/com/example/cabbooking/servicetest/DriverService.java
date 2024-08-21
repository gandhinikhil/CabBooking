package com.example.cabbooking.servicetest;

import com.example.cabbooking.model.Driver;
import com.example.cabbooking.model.Location;
import com.example.cabbooking.model.Vehicle;
import com.example.cabbooking.vo.DriverDetailsVO;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.VehicleDetailsVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverService {
    private final Map<String, Driver> drivers = new HashMap<>();

    public void addDriver(DriverDetailsVO driverDetailsVO) {
        VehicleDetailsVO vehicleDetailsVO = driverDetailsVO.getVehicle();
        LocationDetailsVO locationDetailsVO = driverDetailsVO.getLocation();
        Vehicle vehicle = new Vehicle(vehicleDetailsVO.getModel(), vehicleDetailsVO.getRegistrationNumber());
        Location location = new Location(locationDetailsVO.getX(), locationDetailsVO.getY());
        Driver driver = new Driver(driverDetailsVO.getName(), driverDetailsVO.getGender(), driverDetailsVO.getAge(), vehicle, location, true);
        drivers.put(driver.getName(), driver);
        System.out.println(driver.getName()+" "+driver.getGender()+" "+driver.getAge()+" "+driver.isAvailable()+" "+driver.getCurrentLocation()+" "+driver.getVehicle());
    }

    public List<Driver> findAvailableDrivers(LocationDetailsVO source, int maxDistance) {
        List<Driver> availableDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable() && driver.getCurrentLocation().distanceTo(source) <= maxDistance) {
                availableDrivers.add(driver);
                System.out.println(driver.getName());
            }
        }
        return availableDrivers;
    }

    public Driver getDriver(String driverName) {
        return drivers.get(driverName);
    }
}
