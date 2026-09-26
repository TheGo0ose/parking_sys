package com.parking.parkingsystem.dto;

import com.parking.parkingsystem.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleDto {
    private Long id;
    
    @NotBlank(message = "License plate is required")
    private String licensePlate;
    
    @NotNull(message = "Vehicle type is required")
    private VehicleType type;
}
