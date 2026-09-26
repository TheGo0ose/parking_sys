package com.parking.parkingsystem.controller;

import com.parking.parkingsystem.dto.ParkingSessionDto;
import com.parking.parkingsystem.service.ParkingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class ParkingSessionController {

    private final ParkingSessionService parkingSessionService;

    @PostMapping("/enter")
    public ResponseEntity<ParkingSessionDto> enterParking(@RequestParam Long vehicleId, @RequestParam Long spotId) {
        return new ResponseEntity<>(parkingSessionService.enterParking(vehicleId, spotId), HttpStatus.CREATED);
    }

    @PostMapping("/exit")
    public ResponseEntity<ParkingSessionDto> exitParking(@RequestParam Long vehicleId) {
        return ResponseEntity.ok(parkingSessionService.exitParking(vehicleId));
    }
}
