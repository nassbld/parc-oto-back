package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.dto.AgencyDTO;
import com.bld.parc_oto_back.infrastructure.AgencyRepository;
import com.bld.parc_oto_back.infrastructure.mapper.AgencyMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgencyService {
    private final AgencyRepository agencyRepository;
    private final AgencyMapper agencyMapper;

    public AgencyService(AgencyRepository agencyRepository, AgencyMapper agencyMapper) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
    }

    public Optional<AgencyDTO> getAgencyById(Long id) {
        return agencyRepository.findById(id)
                .map(agencyMapper::toDto);
    }

    public List<AgencyDTO> getAllAgencies() {
        return agencyRepository.findAll().stream()
                .map(agencyMapper::toDto)
                .collect(Collectors.toList());
    }

    public void createAgency(AgencyDTO agencyDTO) {
        Agency agency = agencyMapper.toEntity(agencyDTO);
        agencyRepository.save(agency);
    }

    public void deleteAgency(Long id) {
        agencyRepository.deleteById(id);
    }

    public void updateAgency(AgencyDTO agencyDTO) {
        Optional<Agency> existingAgency = agencyRepository.findById(agencyDTO.getId());
        if (existingAgency.isEmpty()) {
            throw new EntityNotFoundException("Agency not found with id: " + agencyDTO.getId());
        }

        Agency updatedAgency = agencyMapper.toEntity(agencyDTO);
        agencyRepository.save(updatedAgency);
    }
}
