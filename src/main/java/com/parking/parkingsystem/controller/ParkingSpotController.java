package com.parking.parkingsystem.controller;

import com.parking.parkingsystem.dto.ParkingSpotDto;
import com.parking.parkingsystem.service.ParkingSpotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping
    public ResponseEntity<ParkingSpotDto> createSpot(@Valid @RequestBody ParkingSpotDto dto) {
        return new ResponseEntity<>(parkingSpotService.createSpot(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotDto>> getAllSpots() {
        return ResponseEntity.ok(parkingSpotService.getAllSpots());
    }

    @GetMapping("/free")
    public ResponseEntity<List<ParkingSpotDto>> getFreeSpots() {
        return ResponseEntity.ok(parkingSpotService.getFreeSpots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotDto> getSpotById(@PathVariable Long id) {
        return ResponseEntity.ok(parkingSpotService.getSpotById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpotDto> updateSpot(@PathVariable Long id, @Valid @RequestBody ParkingSpotDto dto) {
        return ResponseEntity.ok(parkingSpotService.updateSpot(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpot(@PathVariable Long id) {
        parkingSpotService.deleteSpot(id);
        return ResponseEntity.noContent().build();
    }
}
