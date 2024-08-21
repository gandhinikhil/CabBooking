package com.example.cabbooking.service;

import com.example.cabbooking.model.Driver;
import com.example.cabbooking.model.Location;
import com.example.cabbooking.model.Vehicle;
import com.example.cabbooking.vo.DriverDetailsVO;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.VehicleDetailsVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverServiceTest {

    private DriverService driverService;

    @BeforeEach
    void setUp() {
        driverService = new DriverService();
    }

    @Test
    void testAddDriver() {
        DriverDetailsVO driverDetailsVO = new DriverDetailsVO();
        driverDetailsVO.setName("Driver1");
        driverDetailsVO.setGender("M");
        driverDetailsVO.setAge(30);

        VehicleDetailsVO vehicleDetailsVO = new VehicleDetailsVO();
        vehicleDetailsVO.setModel("Toyota");
        vehicleDetailsVO.setRegistrationNumber("KA-01-12345");
        driverDetailsVO.setVehicle(vehicleDetailsVO);

        LocationDetailsVO locationDetailsVO = new LocationDetailsVO(10, 20);
        driverDetailsVO.setLocation(locationDetailsVO);

        driverService.addDriver(driverDetailsVO);

        Driver driver = driverService.getDriver("Driver1");
        assertNotNull(driver);
        assertEquals("Driver1", driver.getName());
        assertEquals("M", driver.getGender());
        assertEquals(30, driver.getAge());
        assertEquals("Toyota", driver.getVehicle().getModel());
        assertEquals("KA-01-12345", driver.getVehicle().getRegistrationNumber());
        assertEquals(10, driver.getCurrentLocation().getX());
        assertEquals(20, driver.getCurrentLocation().getY());
        assertTrue(driver.isAvailable());
    }

    @Test
    void testFindAvailableDrivers() {
        DriverDetailsVO driverDetailsVO1 = new DriverDetailsVO();
        driverDetailsVO1.setName("Driver1");
        driverDetailsVO1.setGender("M");
        driverDetailsVO1.setAge(30);
        driverDetailsVO1.setVehicle(new VehicleDetailsVO("Toyota", "KA-01-12345"));
        driverDetailsVO1.setLocation(new LocationDetailsVO(10, 20));
        driverService.addDriver(driverDetailsVO1);

        DriverDetailsVO driverDetailsVO2 = new DriverDetailsVO();
        driverDetailsVO2.setName("Driver2");
        driverDetailsVO2.setGender("F");
        driverDetailsVO2.setAge(28);
        driverDetailsVO2.setVehicle(new VehicleDetailsVO("Honda", "KA-02-54321"));
        driverDetailsVO2.setLocation(new LocationDetailsVO(15, 25));
        driverService.addDriver(driverDetailsVO2);

        LocationDetailsVO sourceLocation = new LocationDetailsVO(12, 22);
        List<Driver> availableDrivers = driverService.findAvailableDrivers(sourceLocation, 10);

        assertEquals(1, availableDrivers.size());
        assertEquals("Driver1", availableDrivers.get(0).getName());
    }

    @Test
    void testGetDriver() {
        DriverDetailsVO driverDetailsVO = new DriverDetailsVO();
        driverDetailsVO.setName("Driver1");
        driverDetailsVO.setGender("M");
        driverDetailsVO.setAge(30);
        driverDetailsVO.setVehicle(new VehicleDetailsVO("Toyota", "KA-01-12345"));
        driverDetailsVO.setLocation(new LocationDetailsVO(10, 20));
        driverService.addDriver(driverDetailsVO);

        Driver driver = driverService.getDriver("Driver1");
        assertNotNull(driver);
        assertEquals("Driver1", driver.getName());

        Driver nonExistentDriver = driverService.getDriver("NonExistentDriver");
        assertNull(nonExistentDriver);
    }
}
