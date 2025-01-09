package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Address;
import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.dto.AgencyDTO;
import com.bld.parc_oto_back.infrastructure.AgencyRepository;
import com.bld.parc_oto_back.infrastructure.mapper.AgencyMapper;
import jakarta.persistence.EntityNotFoundException;
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

class AgencyServiceTest {

    @Mock
    private AgencyRepository agencyRepository;

    @InjectMocks
    private AgencyMapper agencyMapper;

    @InjectMocks
    private AgencyService agencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAgencyById_shouldReturnAgency_whenAgencyExists() {
        Agency agency = new Agency();
        agency.setId(1L);
        when(agencyRepository.findById(1L)).thenReturn(Optional.of(agency));

        Optional<AgencyDTO> result = agencyService.getAgencyById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getAllAgencies_shouldReturnListOfAgencies() {
        List<Agency> agencies = Arrays.asList(new Agency(), new Agency());
        when(agencyRepository.findAll()).thenReturn(agencies);

        List<AgencyDTO> result = agencyService.getAllAgencies();

        assertEquals(2, result.size());
    }

    @Test
    void createAgency_shouldSaveAgency() {
        Agency agency = new Agency();
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);
        agencyService.createAgency(agencyDTO);

        verify(agencyRepository, times(1)).save(agency);
    }

    @Test
    void deleteAgency_shouldDeleteAgency() {
        agencyService.deleteAgency(1L);

        verify(agencyRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateAgency_shouldUpdateExistingAgency() {
        Agency existingAgency = new Agency();
        existingAgency.setId(1L);
        existingAgency.setName("Old Name");
        existingAgency.setAddress(new Address("Old Street", "12345", "Old City", "USA"));  // Utilisation de l'entité Address

        Agency updatedAgency = new Agency();
        updatedAgency.setId(1L);
        updatedAgency.setName("New Name");
        updatedAgency.setAddress(new Address("New Street", "67890", "New City", "USA"));  // Nouvelle Address

        when(agencyRepository.findById(1L)).thenReturn(Optional.of(existingAgency));

        AgencyDTO updatedAgencyDTO = agencyMapper.toDto(updatedAgency);

        agencyService.updateAgency(updatedAgencyDTO);

        verify(agencyRepository, times(1)).save(existingAgency);
        assertEquals("New Name", existingAgency.getName());
        assertEquals("New Street", existingAgency.getAddress().getStreet());
        assertEquals("New City", existingAgency.getAddress().getCity());
        assertEquals("67890", existingAgency.getAddress().getPostalCode());
    }

    @Test
    void updateAgency_shouldThrowException_whenAgencyNotFound() {
        Agency agency = new Agency();
        agency.setId(1L);

        when(agencyRepository.findById(1L)).thenReturn(Optional.empty());

        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        assertThrows(EntityNotFoundException.class, () -> agencyService.updateAgency(agencyDTO));
    }
}
