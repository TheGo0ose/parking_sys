package com.parking.parkingsystem.service;

import com.parking.parkingsystem.dto.VehicleDto;
import com.parking.parkingsystem.exception.BadRequestException;
import com.parking.parkingsystem.exception.ResourceNotFoundException;
import com.parking.parkingsystem.model.Vehicle;
import com.parking.parkingsystem.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleDto createVehicle(VehicleDto dto) {
        if (vehicleRepository.existsByLicensePlate(dto.getLicensePlate())) {
            throw new BadRequestException("Vehicle with this license plate already exists");
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setType(dto.getType());
        
        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToDto(saved);
    }

    public List<VehicleDto> getAllVehicles() {
        return vehicleRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = getVehicleEntityById(id);
        return mapToDto(vehicle);
    }

    public Vehicle getVehicleEntityById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    public VehicleDto updateVehicle(Long id, VehicleDto dto) {
        Vehicle vehicle = getVehicleEntityById(id);
        
        if (!vehicle.getLicensePlate().equals(dto.getLicensePlate()) && 
            vehicleRepository.existsByLicensePlate(dto.getLicensePlate())) {
            throw new BadRequestException("Vehicle with this license plate already exists");
        }

        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setType(dto.getType());
        
        return mapToDto(vehicleRepository.save(vehicle));
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicleEntityById(id);
        vehicleRepository.delete(vehicle);
    }

    private VehicleDto mapToDto(Vehicle vehicle) {
        VehicleDto dto = new VehicleDto();
        dto.setId(vehicle.getId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setType(vehicle.getType());
        return dto;
    }
}
