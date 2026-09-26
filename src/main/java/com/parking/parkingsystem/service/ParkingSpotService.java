package com.parking.parkingsystem.service;

import com.parking.parkingsystem.dto.ParkingSpotDto;
import com.parking.parkingsystem.exception.BadRequestException;
import com.parking.parkingsystem.exception.ResourceNotFoundException;
import com.parking.parkingsystem.model.ParkingSpot;
import com.parking.parkingsystem.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotDto createSpot(ParkingSpotDto dto) {
        if (parkingSpotRepository.existsBySpotNumber(dto.getSpotNumber())) {
            throw new BadRequestException("Spot with this number already exists");
        }
        ParkingSpot spot = new ParkingSpot();
        spot.setSpotNumber(dto.getSpotNumber());
        spot.setType(dto.getType());
        spot.setOccupied(false);

        return mapToDto(parkingSpotRepository.save(spot));
    }

    public List<ParkingSpotDto> getAllSpots() {
        return parkingSpotRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ParkingSpotDto getSpotById(Long id) {
        return mapToDto(getSpotEntityById(id));
    }

    public ParkingSpot getSpotEntityById(Long id) {
        return parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking spot not found with id: " + id));
    }

    public List<ParkingSpotDto> getFreeSpots() {
        return parkingSpotRepository.findByIsOccupiedFalse().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ParkingSpotDto updateSpot(Long id, ParkingSpotDto dto) {
        ParkingSpot spot = getSpotEntityById(id);

        if (!spot.getSpotNumber().equals(dto.getSpotNumber()) &&
            parkingSpotRepository.existsBySpotNumber(dto.getSpotNumber())) {
            throw new BadRequestException("Spot with this number already exists");
        }

        spot.setSpotNumber(dto.getSpotNumber());
        spot.setType(dto.getType());
        
        return mapToDto(parkingSpotRepository.save(spot));
    }

    public void deleteSpot(Long id) {
        ParkingSpot spot = getSpotEntityById(id);
        parkingSpotRepository.delete(spot);
    }

    public void save(ParkingSpot spot) {
        parkingSpotRepository.save(spot);
    }

    private ParkingSpotDto mapToDto(ParkingSpot spot) {
        ParkingSpotDto dto = new ParkingSpotDto();
        dto.setId(spot.getId());
        dto.setSpotNumber(spot.getSpotNumber());
        dto.setType(spot.getType());
        dto.setOccupied(spot.isOccupied());
        return dto;
    }
}
