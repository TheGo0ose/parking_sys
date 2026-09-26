package com.parking.parkingsystem.repository;

import com.parking.parkingsystem.model.ParkingSession;
import com.parking.parkingsystem.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    Optional<ParkingSession> findByVehicleAndExitTimeIsNull(Vehicle vehicle);
}
