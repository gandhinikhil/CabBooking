package com.example.cabbooking.repository;
import com.example.cabbooking.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride,Long> {
   Optional<Ride>findByUserNameAndDriverName(String userName, String driverName);
}
