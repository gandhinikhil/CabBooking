package com.example.cabbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String model;
    @Column(unique = true, nullable = false)
    private String registrationNumber;
    @OneToOne(mappedBy = "vehicle")
    private Driver driver;
    private LocalDateTime localDateTime = LocalDateTime.now();
}