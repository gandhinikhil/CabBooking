package com.example.cabbooking.servicetest;

import com.example.cabbooking.controller.CabBookingController;
import com.example.cabbooking.entity.Driver;
import com.example.cabbooking.entity.Location;
import com.example.cabbooking.entity.Vehicle;
import com.example.cabbooking.repository.DriverRepository;
import com.example.cabbooking.repository.LocationRepository;
import com.example.cabbooking.repository.VehicleRepository;
import com.example.cabbooking.vo.DriverDetailsVO;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.RideDetailsVO;
import com.example.cabbooking.vo.VehicleDetailsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(CabBookingController.class);

    public void addDriver(DriverDetailsVO driverDetailsVO) {
        VehicleDetailsVO vehicleDetailsVO = driverDetailsVO.getVehicle();
        LocationDetailsVO locationDetailsVO = driverDetailsVO.getLocation();
        VehicleDetailsVO vehicle = new VehicleDetailsVO(vehicleDetailsVO.getModel(), vehicleDetailsVO.getRegistrationNumber());
        LocationDetailsVO location = new LocationDetailsVO(locationDetailsVO.getXDistance(), locationDetailsVO.getYDistance());
        DriverDetailsVO driver = new DriverDetailsVO(driverDetailsVO.getName(), driverDetailsVO.getGender(), driverDetailsVO.getAge(), vehicle, location, true);
        drivers.put(driver.getName(), driver);
        System.out.println(driver.getName() + " " + driver.getGender() + " " + driver.getAge() + " " + driver.isAvailable() + " " + driver.getLocation() + " " + driver.getVehicle());
    }

    public void saveDriver(DriverDetailsVO driverDetailsVO) {

        Vehicle vehicle = new Vehicle();
        vehicle.setModel(driverDetailsVO.getVehicle().getModel());
        vehicle.setRegistrationNumber(driverDetailsVO.getVehicle().getRegistrationNumber());

        Location location = new Location();
        location.setXDistance(driverDetailsVO.getLocation().getXDistance());
        location.setYDistance(driverDetailsVO.getLocation().getYDistance());

        Driver driver = new Driver();
        driver.setName(driverDetailsVO.getName());
        driver.setAge(driverDetailsVO.getAge());
        driver.setGender(driverDetailsVO.getGender());
        driver.setAvailable(driverDetailsVO.isAvailable());
        driver.setVehicle(vehicle);
        driver.setLocation(location);
        //Eastablish connection between driver and location ,driver and vehcile.
        vehicle.setDriver(driver);
        //location.setDriver(driver);
        driverRepository.save(driver);
        vehicleRepository.save(vehicle);
        locationRepository.save(location);

    }

    public List<RideDetailsVO> findAvailableDrivers(LocationDetailsVO source, LocationDetailsVO destination, int maxDistance) {
        List<RideDetailsVO> availableDrivers = new ArrayList<>();
        for (DriverDetailsVO driver : drivers.values()) {
            if (driver.isAvailable() && driver.getLocation().distanceTo(source) <= maxDistance) {
                double fare = rideService.calculateFare(source, destination);
                availableDrivers.add(new RideDetailsVO(driver, Math.round(fare * 100.0) / 100.0));
                System.out.println(driver.getName());
            }
        }
        rideService.sortedDriversByDistanceToSource(source, availableDrivers);
        return availableDrivers;
    }

    public DriverDetailsVO getDriver(String driverName) {
        return drivers.get(driverName);
    }
}
