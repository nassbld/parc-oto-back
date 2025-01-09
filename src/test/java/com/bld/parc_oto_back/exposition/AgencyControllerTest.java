package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.AgencyService;
import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.domain.Address;
import com.bld.parc_oto_back.dto.AgencyDTO;
import com.bld.parc_oto_back.infrastructure.mapper.AgencyMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AgencyController.class)
class AgencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgencyService agencyService;

    @MockBean
    private AgencyMapper agencyMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAgencyById_shouldReturnAgency_whenAgencyExists() throws Exception {
        Agency agency = new Agency(1L, new Address("123 Main St", "12345", "City", "Country"), "Test Agency", new ArrayList<>());
        when(agencyService.getAgencyById(1L)).thenReturn(Optional.of(agency).map(agencyMapper::toDto));

        mockMvc.perform(get("/agencies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Agency"));
    }

    @Test
    void getAgencyById_shouldReturnNotFound_whenAgencyDoesNotExist() throws Exception {
        when(agencyService.getAgencyById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/agencies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAgency_shouldReturnNoContent() throws Exception {
        Agency agency = new Agency(null, new Address("123 Main St", "12345", "City", "Country"), "New Agency", new ArrayList<>());

        mockMvc.perform(post("/agencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agency)))
                .andExpect(status().isNoContent());

        verify(agencyService, times(1)).createAgency(any(AgencyDTO.class));
    }

    @Test
    void updateAgency_shouldReturnNoContent_whenIdMatches() throws Exception {
        Agency agency = new Agency(1L, new Address("123 Main St", "12345", "City", "Country"), "Updated Agency", new ArrayList<>());

        mockMvc.perform(put("/agencies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agency)))
                .andExpect(status().isNoContent());

        verify(agencyService, times(1)).updateAgency(any(AgencyDTO.class));
    }

    @Test
    void updateAgency_shouldReturnBadRequest_whenIdDoesNotMatch() throws Exception {
        Agency agency = new Agency(2L, new Address("123 Main St", "12345", "City", "Country"), "Updated Agency", new ArrayList<>());

        mockMvc.perform(put("/agencies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agency)))
                .andExpect(status().isBadRequest());

        verify(agencyService, never()).updateAgency(any(AgencyDTO.class));
    }

    @Test
    void deleteAgency_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/agencies/1"))
                .andExpect(status().isNoContent());

        verify(agencyService, times(1)).deleteAgency(1L);
    }
}
