package com.parking.parkingsystem.repository;

import com.parking.parkingsystem.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByIsOccupiedFalse();
    boolean existsBySpotNumber(String spotNumber);
}
