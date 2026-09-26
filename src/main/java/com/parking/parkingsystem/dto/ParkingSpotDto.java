package com.parking.parkingsystem.dto;

import com.parking.parkingsystem.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParkingSpotDto {
    private Long id;

    @NotBlank(message = "Spot number is required")
    private String spotNumber;

    @NotNull(message = "Spot type is required")
    private VehicleType type;

    private boolean isOccupied;
}
