package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.VehicleService;
import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllVehicles_shouldReturnListOfVehicles() throws Exception {
        Vehicle vehicle1 = new Vehicle(1L, "ABC123", "Model X", "Tesla", VehicleStatus.AVAILABLE);
        Vehicle vehicle2 = new Vehicle(2L, "DEF456", "Civic", "Honda", VehicleStatus.UNDER_MAINTENANCE);
        when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList(vehicle1, vehicle2));

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getVehicleById_shouldReturnVehicle_whenVehicleExists() throws Exception {
        Vehicle vehicle = new Vehicle(1L, "ABC123", "Model X", "Tesla", VehicleStatus.AVAILABLE);
        when(vehicleService.getVehicleById(1L)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.license_plate").value("ABC123"))
                .andExpect(jsonPath("$.model").value("Model X"))
                .andExpect(jsonPath("$.brand").value("Tesla"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void getVehicleById_shouldReturnNotFound_whenVehicleDoesNotExist() throws Exception {
        when(vehicleService.getVehicleById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createVehicle_shouldReturnNoContent() throws Exception {
        Vehicle vehicle = new Vehicle(null, "GHI789", "Corolla", "Toyota", VehicleStatus.AVAILABLE);

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).addVehicle(any(Vehicle.class));
    }

    @Test
    void deleteVehicle_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).deleteVehicle(1L);
    }
}
