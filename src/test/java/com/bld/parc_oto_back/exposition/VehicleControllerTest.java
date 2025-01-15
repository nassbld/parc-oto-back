package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.VehicleService;
import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.domain.VehicleType;
import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import com.bld.parc_oto_back.dto.VehicleDTO;
import com.bld.parc_oto_back.infrastructure.mapper.VehicleMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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

    @MockBean
    private VehicleMapper vehicleMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllVehicles_shouldReturnListOfVehicles() throws Exception {
        Agency testAgency = new Agency();
        testAgency.setId(1L);
        testAgency.setName("Test Agency");

        VehicleType type1 = new VehicleType();
        type1.setId(1L);
        type1.setBrand("Citroen");
        type1.setModel("C3");
        type1.setImageUrl("url_to_c3_image");

        VehicleType type2 = new VehicleType();
        type2.setId(2L);
        type2.setBrand("Citroen");
        type2.setModel("Jumper");
        type2.setImageUrl("url_to_jumper_image");

        LocalDateTime now = LocalDateTime.now();
        Vehicle vehicle1 = new Vehicle(1L, "ABC123", type1, VehicleStatus.AVAILABLE, testAgency, now.plusYears(1), now.plusMonths(6));
        Vehicle vehicle2 = new Vehicle(2L, "DEF456", type2, VehicleStatus.UNDER_MAINTENANCE, testAgency, now.plusYears(1), now.plusMonths(6));
        VehicleDTO vehicleDTO1 = vehicleMapper.toDto(vehicle1);
        VehicleDTO vehicleDTO2 = vehicleMapper.toDto(vehicle2);
        when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList(vehicleDTO1, vehicleDTO2));

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getVehicleById_shouldReturnVehicle_whenVehicleExists() throws Exception {
        Agency testAgency = new Agency();
        testAgency.setId(1L);
        testAgency.setName("Test Agency");

        VehicleType type = new VehicleType();
        type.setId(1L);
        type.setBrand("Citroen");
        type.setModel("C3");
        type.setImageUrl("url_to_c3_image");

        LocalDateTime now = LocalDateTime.now();
        Vehicle vehicle = new Vehicle(1L, "ABC123", type, VehicleStatus.AVAILABLE, testAgency, now.plusYears(1), now.plusMonths(6));
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);
        when(vehicleService.getVehicleById(1L)).thenReturn(Optional.of(vehicleDTO));

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.licensePlate").value("ABC123"))
                .andExpect(jsonPath("$.vehicleType.brand").value("Citroen"))
                .andExpect(jsonPath("$.vehicleType.model").value("C3"))
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
        Agency testAgency = new Agency();
        testAgency.setId(1L);
        testAgency.setName("Test Agency");

        VehicleType type = new VehicleType();
        type.setId(1L);
        type.setBrand("Citroen");
        type.setModel("C3");
        type.setImageUrl("url_to_c3_image");

        LocalDateTime now = LocalDateTime.now();
        Vehicle vehicle = new Vehicle(1L, "ABC123", type, VehicleStatus.AVAILABLE, testAgency, now.plusYears(1), now.plusMonths(6));

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicle)))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).addVehicle(any(VehicleDTO.class));
    }

    @Test
    void deleteVehicle_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/vehicles/1"))
                .andExpect(status().isNoContent());

        verify(vehicleService, times(1)).deleteVehicle(1L);
    }
}
