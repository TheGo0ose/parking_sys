package com.parking.parkingsystem.service;

import com.parking.parkingsystem.dto.ParkingSessionDto;
import com.parking.parkingsystem.exception.BadRequestException;
import com.parking.parkingsystem.exception.ResourceNotFoundException;
import com.parking.parkingsystem.model.ParkingSession;
import com.parking.parkingsystem.model.ParkingSpot;
import com.parking.parkingsystem.model.Vehicle;
import com.parking.parkingsystem.repository.ParkingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingSessionService {

    private final ParkingSessionRepository sessionRepository;
    private final VehicleService vehicleService;
    private final ParkingSpotService parkingSpotService;

    public ParkingSessionDto enterParking(Long vehicleId, Long spotId) {
        Vehicle vehicle = vehicleService.getVehicleEntityById(vehicleId);
        ParkingSpot spot = parkingSpotService.getSpotEntityById(spotId);

        if (sessionRepository.findByVehicleAndExitTimeIsNull(vehicle).isPresent()) {
            throw new BadRequestException("Vehicle is already parked");
        }

        if (spot.isOccupied()) {
            throw new BadRequestException("Parking spot is already occupied");
        }

        if (vehicle.getType() != spot.getType()) {
            throw new BadRequestException("Vehicle type does not match parking spot type");
        }

        spot.setOccupied(true);
        parkingSpotService.save(spot);

        ParkingSession session = new ParkingSession();
        session.setVehicle(vehicle);
        session.setParkingSpot(spot);
        session.setEntryTime(LocalDateTime.now());

        return mapToDto(sessionRepository.save(session));
    }

    public ParkingSessionDto exitParking(Long vehicleId) {
        Vehicle vehicle = vehicleService.getVehicleEntityById(vehicleId);
        
        ParkingSession session = sessionRepository.findByVehicleAndExitTimeIsNull(vehicle)
                .orElseThrow(() -> new ResourceNotFoundException("No active parking session found for vehicle id: " + vehicleId));

        session.setExitTime(LocalDateTime.now());
        
        // Calculate cost
        long hours = Duration.between(session.getEntryTime(), session.getExitTime()).toHours();
        if (hours == 0) hours = 1; // Minimum 1 hour charge
        else if (Duration.between(session.getEntryTime(), session.getExitTime()).toMinutesPart() > 0) {
            hours += 1; // Round up to next hour
        }

        BigDecimal rate = switch (vehicle.getType()) {
            case CAR -> BigDecimal.valueOf(100);
            case MOTORCYCLE -> BigDecimal.valueOf(50);
            case TRUCK -> BigDecimal.valueOf(150);
        };

        session.setCost(rate.multiply(BigDecimal.valueOf(hours)));

        // Free the spot
        ParkingSpot spot = session.getParkingSpot();
        spot.setOccupied(false);
        parkingSpotService.save(spot);

        return mapToDto(sessionRepository.save(session));
    }

    private ParkingSessionDto mapToDto(ParkingSession session) {
        ParkingSessionDto dto = new ParkingSessionDto();
        dto.setId(session.getId());
        dto.setVehicleId(session.getVehicle().getId());
        dto.setLicensePlate(session.getVehicle().getLicensePlate());
        dto.setSpotId(session.getParkingSpot().getId());
        dto.setSpotNumber(session.getParkingSpot().getSpotNumber());
        dto.setEntryTime(session.getEntryTime());
        dto.setExitTime(session.getExitTime());
        dto.setCost(session.getCost());
        return dto;
    }
}
