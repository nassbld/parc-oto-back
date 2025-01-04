package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.domain.enums.ReservationStatus;
import com.bld.parc_oto_back.infrastructure.AgencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgencyService {
    private final AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public Optional<Agency> getAgencyById(Long id) {
        return agencyRepository.findById(id);
    }

    public List<Agency> getAllAgencies() {
        return agencyRepository.findAll();
    }

    public void createAgency(Agency agency) {
        agencyRepository.save(agency);
    }

    public void deleteAgency(Long id) {
        agencyRepository.deleteById(id);
    }

    public void updateAgency(Agency agency) {
        Optional<Agency> existingAgency = agencyRepository.findById(agency.getId());
        if (existingAgency.isEmpty()) {
            throw new EntityNotFoundException("Agency not found with id: " + agency.getId());
        }

        Agency updatedAgency = existingAgency.get();
        updatedAgency.setName(agency.getName());
        updatedAgency.setAddress(agency.getAddress());

        agencyRepository.save(updatedAgency);
    }

}
