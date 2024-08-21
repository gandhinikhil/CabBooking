package com.example.cabbooking.controllertest;

import com.example.cabbooking.controller.CabBookingController;
import com.example.cabbooking.model.Driver;
import com.example.cabbooking.servicetest.DriverService;
import com.example.cabbooking.servicetest.RideService;

import com.example.cabbooking.servicetest.UserServiceTest;
import com.example.cabbooking.vo.DriverDetailsVO;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.UserDetailsInVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CabBookingControllerTest {

    @InjectMocks
    private CabBookingController cabBookingController;

    @Mock
    private RideService rideService;

    @Mock
    private DriverService driverService;

    @Mock
    private UserServiceTest userService;
//test Classes
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser_Success() {
        UserDetailsInVO userDetails = new UserDetailsInVO();
        doNothing().when(userService).addUser(any(UserDetailsInVO.class));

        ResponseEntity<String> response = cabBookingController.addUser(userDetails);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User added successfully", response.getBody());
        verify(userService, times(1)).addUser(userDetails);
    }

    @Test
    void addUser_Failure() {
        UserDetailsInVO userDetails = new UserDetailsInVO();
        doThrow(new RuntimeException()).when(userService).addUser(any(UserDetailsInVO.class));

        ResponseEntity<String> response = cabBookingController.addUser(userDetails);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to add user", response.getBody());
        verify(userService, times(1)).addUser(userDetails);
    }

    @Test
    void addDriver_Success() {
        DriverDetailsVO driverDetails = new DriverDetailsVO();
        doNothing().when(driverService).addDriver(any(DriverDetailsVO.class));

        ResponseEntity<String> response = cabBookingController.addDriver(driverDetails);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Driver added Successfully", response.getBody());
        verify(driverService, times(1)).addDriver(driverDetails);
    }

    @Test
    void addDriver_Failure() {
        DriverDetailsVO driverDetails = new DriverDetailsVO();
        doThrow(new RuntimeException()).when(driverService).addDriver(any(DriverDetailsVO.class));

        ResponseEntity<String> response = cabBookingController.addDriver(driverDetails);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to add Driver", response.getBody());
        verify(driverService, times(1)).addDriver(driverDetails);
    }

    @Test
    void findRide_Success() {
        List<Integer> source = Arrays.asList(1, 1);
        List<Integer> destination = Arrays.asList(2, 2);
        List<Driver> drivers = Arrays.asList(new Driver());
        when(rideService.findRide(anyString(), any(LocationDetailsVO.class), any(LocationDetailsVO.class)))
                .thenReturn(drivers);

        ResponseEntity<List<Driver>> response = cabBookingController.findRide("username", source, destination);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(drivers, response.getBody());
        verify(rideService, times(1)).findRide(anyString(), any(LocationDetailsVO.class), any(LocationDetailsVO.class));
    }

    @Test
    void findRide_NoContent() {
        List<Integer> source = Arrays.asList(1, 1);
        List<Integer> destination = Arrays.asList(2, 2);
        when(rideService.findRide(anyString(), any(LocationDetailsVO.class), any(LocationDetailsVO.class)))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<Driver>> response = cabBookingController.findRide("username", source, destination);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(rideService, times(1)).findRide(anyString(), any(LocationDetailsVO.class), any(LocationDetailsVO.class));
    }

    @Test
    void chooseRide_Success() {
        doNothing().when(rideService).chooseRide(anyString(), anyString());

        ResponseEntity<String> response = cabBookingController.chooseRide("username", "driverName");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Ride Choosen Succesfully", response.getBody());
        verify(rideService, times(1)).chooseRide("username", "driverName");
    }

    @Test
    void chooseRide_Failure() {
        doThrow(new RuntimeException()).when(rideService).chooseRide(anyString(), anyString());

        ResponseEntity<String> response = cabBookingController.chooseRide("username", "driverName");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to Choose Ride", response.getBody());
        verify(rideService, times(1)).chooseRide("username", "driverName");
    }
}
