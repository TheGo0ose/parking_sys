package com.parking.parkingsystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParkingSessionDto {
    private Long id;
    private Long vehicleId;
    private String licensePlate;
    private Long spotId;
    private String spotNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private BigDecimal cost;
}
