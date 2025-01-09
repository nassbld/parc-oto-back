package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.dto.VehicleDTO;
import com.bld.parc_oto_back.infrastructure.VehicleRepository;
import com.bld.parc_oto_back.infrastructure.mapper.VehicleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleMapper vehicleMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getVehicleById_shouldReturnVehicle_whenVehicleExists() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        Optional<VehicleDTO> result = vehicleService.getVehicleById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getAllVehicles_shouldReturnListOfVehicles() {
        List<Vehicle> vehicles = Arrays.asList(new Vehicle(), new Vehicle());
        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<VehicleDTO> result = vehicleService.getAllVehicles();

        assertEquals(2, result.size());
    }

    @Test
    void addVehicle_shouldSaveVehicle() {
        Vehicle vehicle = new Vehicle();

        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        vehicleService.addVehicle(vehicleDTO);

        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void deleteVehicle_shouldDeleteVehicle() {
        Long vehicleId = 1L;

        vehicleService.deleteVehicle(vehicleId);

        verify(vehicleRepository).deleteById(vehicleId);
    }
}
