package com.example.cabbooking.controller;

import com.example.cabbooking.customExecption.AlreadyCanceledException;
import com.example.cabbooking.customExecption.RideNotFoundException;
import com.example.cabbooking.servicetest.DriverService;
import com.example.cabbooking.servicetest.RideService;
import com.example.cabbooking.servicetest.UserService;
import com.example.cabbooking.vo.DriverDetailsVO;
import com.example.cabbooking.vo.RideDetailsVO;
import com.example.cabbooking.vo.LocationDetailsVO;
import com.example.cabbooking.vo.UserDetailsInVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cabbooking")
public class CabBookingController {

    private final RideService rideService;

    private final DriverService driverService;

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CabBookingController.class);

    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody UserDetailsInVO userDetails) {
        try {
            logger.info("trying to add data");
            userService.addUser(userDetails);
            logger.info("trying to add data in Local storage");
            userService.saveUser(userDetails);
            logger.info("Data save successfully in DB");
            return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add user", HttpStatus.BAD_REQUEST);
        }
        // How we can return here as response entity.
    }

    @PostMapping("/add-driver")
    public ResponseEntity<String> addDriver(@RequestBody DriverDetailsVO driverDetailsVO) {
        try {
            logger.info("Trying to add driver data");
            driverService.addDriver(driverDetailsVO);
            logger.info("Trying to add driver data in local storage");
            driverService.saveDriver(driverDetailsVO);
            logger.info("Trying to add driver data in DB");
            return new ResponseEntity<>("Driver added Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add Driver", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-ride")
    public ResponseEntity<List<RideDetailsVO>> findRide(@RequestParam String username, @RequestParam List<Integer> source, @RequestParam List<Integer> destination) {
        logger.info("Inside findRide Method");
        try {

            List<RideDetailsVO> availableDrivers = rideService.findRide(username, new LocationDetailsVO(source.get(0), source.get(1)), new LocationDetailsVO(destination.get(0), destination.get(1)));
            logger.info("available Drivers " + availableDrivers.size() + " Size of list");
            if (availableDrivers.isEmpty()) {
                ResponseEntity<List<RideDetailsVO>> listResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
                if (!ObjectUtils.isEmpty(listResponseEntity))
                    return listResponseEntity;


            }
            return new ResponseEntity<>(availableDrivers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred with Exception " + e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // How we return here http request here
    }

    @PostMapping("/choose-ride")
    public synchronized ResponseEntity<String> chooseRide(@RequestParam String userName, @RequestParam String driverName) {
        try {
            rideService.chooseRide(userName, driverName);
            return new ResponseEntity<>("Ride Chosen Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Choose Ride", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancel-ride")
    public ResponseEntity<String> cancelRide(@RequestParam String userName, @RequestParam String driverName) {
        try {
            rideService.cancelRide(userName, driverName);
            return new ResponseEntity<>("Ride Cancel Successfully", HttpStatus.OK);
        } catch (RideNotFoundException e) {
            logger.error("Ride not found for user: " + userName + " with driver: " + driverName);
            return new ResponseEntity<>("Ride not found", HttpStatus.NOT_FOUND);
        } catch (AlreadyCanceledException e) {
            logger.error("Ride Already Canceled for user: " + userName + " with driver: " + driverName);
            return new ResponseEntity<>("Ride Already canceled", HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Failed to cancel ride for user: " + userName + " with driver: " + driverName);
            return new ResponseEntity<>("Failed to Cancel Ride", HttpStatus.BAD_REQUEST);
        }
    }
}



