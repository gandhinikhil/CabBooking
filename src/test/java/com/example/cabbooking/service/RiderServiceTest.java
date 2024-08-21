package com.example.cabbooking.service;


import com.example.cabbooking.model.Driver;
import com.example.cabbooking.model.Location;
import com.example.cabbooking.model.User;
import com.example.cabbooking.vo.LocationDetailsVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RideServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private DriverService driverService;

    @InjectMocks
    private RideService rideService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindRide_UserFoundAndDriversAvailable() {
        String username = "John";
        LocationDetailsVO source = new LocationDetailsVO(10, 10);
        LocationDetailsVO destination = new LocationDetailsVO(20, 20);

        User mockUser = new User("John", "M", 30);
        Driver mockDriver = new Driver("Driver1", "M", 25, null, new Location(10, 10), true);

        when(userService.getUser(username)).thenReturn(mockUser);
        when(driverService.findAvailableDrivers(source, 5)).thenReturn(List.of(mockDriver));

        List<Driver> drivers = rideService.findRide(username, source, destination);

        assertNotNull(drivers);
        assertEquals(1, drivers.size());
        assertEquals("Driver1", drivers.get(0).getName());

        verify(userService, times(1)).getUser(username);
        verify(driverService, times(1)).findAvailableDrivers(source, 5);
    }

    @Test
    void testFindRide_UserNotFound() {
        String username = "John";
        LocationDetailsVO source = new LocationDetailsVO(10, 10);
        LocationDetailsVO destination = new LocationDetailsVO(20, 20);

        when(userService.getUser(username)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rideService.findRide(username, source, destination);
        });

        assertEquals("User not found", exception.getMessage());

        verify(userService, times(1)).getUser(username);
        verify(driverService, never()).findAvailableDrivers(any(LocationDetailsVO.class), anyInt());
    }

    @Test
    void testFindRide_NoDriversAvailable() {
        String username = "John";
        LocationDetailsVO source = new LocationDetailsVO(10, 10);
        LocationDetailsVO destination = new LocationDetailsVO(20, 20);

        User mockUser = new User("John", "M", 30);

        when(userService.getUser(username)).thenReturn(mockUser);
        when(driverService.findAvailableDrivers(source, 5)).thenReturn(new ArrayList<>());

        List<Driver> drivers = rideService.findRide(username, source, destination);

        assertNotNull(drivers);
        assertTrue(drivers.isEmpty());

        verify(userService, times(1)).getUser(username);
        verify(driverService, times(1)).findAvailableDrivers(source, 5);
    }

    @Test
    void testChooseRide_DriverAvailable() {
        String username = "John";
        String driverName = "Driver1";

        Driver mockDriver = new Driver("Driver1", "M", 25, null, new Location(10, 10), true);

        when(driverService.getDriver(driverName)).thenReturn(mockDriver);

        rideService.chooseRide(username, driverName);

        assertFalse(mockDriver.isAvailable());

        verify(driverService, times(1)).getDriver(driverName);
    }

    @Test
    void testChooseRide_DriverNotAvailable() {
        String username = "John";
        String driverName = "Driver1";

        Driver mockDriver = new Driver("Driver1", "M", 25, null, new Location(10, 10), false);

        when(driverService.getDriver(driverName)).thenReturn(mockDriver);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rideService.chooseRide(username, driverName);
        });

        assertEquals("Driver not found or not available", exception.getMessage());

        verify(driverService, times(1)).getDriver(driverName);
    }

    @Test
    void testChooseRide_DriverNotFound() {
        String username = "John";
        String driverName = "NonExistentDriver";

        when(driverService.getDriver(driverName)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            rideService.chooseRide(username, driverName);
        });

        assertEquals("Driver not found or not available", exception.getMessage());

        verify(driverService, times(1)).getDriver(driverName);
    }
}

