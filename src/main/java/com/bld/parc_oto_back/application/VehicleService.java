package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.domain.VehicleType;
import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import com.bld.parc_oto_back.dto.VehicleDTO;
import com.bld.parc_oto_back.dto.VehicleIdentityDTO;
import com.bld.parc_oto_back.infrastructure.AgencyRepository;
import com.bld.parc_oto_back.infrastructure.VehicleRepository;
import com.bld.parc_oto_back.infrastructure.VehicleTypeRepository;
import com.bld.parc_oto_back.infrastructure.mapper.VehicleMapper;
import io.micrometer.observation.ObservationFilter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleMapper vehicleMapper;

    private final AgencyRepository agencyRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleTypeRepository vehicleTypeRepository, VehicleMapper vehicleMapper, AgencyRepository agencyRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleMapper = vehicleMapper;
        this.agencyRepository = agencyRepository;
    }

    public Optional<VehicleDTO> getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .filter(vehicle -> !vehicle.isDeleted())
                .map(vehicleMapper::toDto);
    }

    public Optional<VehicleDTO> getVehicleByIdWithDeleted(Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::toDto);
    }

    public Optional<VehicleIdentityDTO> getVehicleIdentityById(Long id) {
        return vehicleRepository.findById(id)
                .filter(vehicle -> !vehicle.isDeleted())
                .map(this::toVehicleIdentityDTO);
    }

    public Optional<VehicleIdentityDTO> getVehicleIdentityByIdWithDeleted(Long id) {
        return vehicleRepository.findById(id)
                .map(this::toVehicleIdentityDTO);
    }

    public List<VehicleDTO> getVehiclesByAgencyId(Long agencyId) {
        return vehicleRepository.findByAgencyId(agencyId).stream()
                .filter(vehicle -> !vehicle.isDeleted())
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VehicleIdentityDTO> getVehiclesIdentityByAgencyId(Long agencyId) {
        return vehicleRepository.findByAgencyId(agencyId).stream()
                .filter(vehicle -> !vehicle.isDeleted())
                .map(this::toVehicleIdentityDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleIdentityDTO> getVehiclesIdentityByAgencyIds(List<Long> agencyIds) {
        return agencyIds.stream()
                .flatMap(agencyId -> getVehiclesIdentityByAgencyId(agencyId).stream())
                .collect(Collectors.toList());
    }

    private VehicleIdentityDTO toVehicleIdentityDTO(Vehicle vehicle) {
        VehicleIdentityDTO vehicleIdentityDTO = new VehicleIdentityDTO();
        vehicleIdentityDTO.setVehicleId(vehicle.getId());
        vehicleIdentityDTO.setLicensePlate(vehicle.getLicensePlate());
        vehicleIdentityDTO.setAgencyId(vehicle.getAgency().getId());

        Long vehicleTypeId = vehicle.getType().getId();
        vehicleIdentityDTO.setVehicleTypeId(vehicleTypeId);

        Optional<VehicleType> vehicleTypeOptional = vehicleTypeRepository.findById(vehicleTypeId);
        if (vehicleTypeOptional.isPresent()) {
            VehicleType vehicleType = vehicleTypeOptional.get();
            vehicleIdentityDTO.setBrand(vehicleType.getBrand());
            vehicleIdentityDTO.setModel(vehicleType.getModel());
            vehicleIdentityDTO.setImageUrl(vehicleType.getImageUrl());
        }

        return vehicleIdentityDTO;
    }

    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .filter(vehicle -> !vehicle.isDeleted())
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VehicleDTO> getAllVehiclesWithDeleted() {
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addVehicle(VehicleDTO vehicleDTO) {
        agencyRepository.findById(vehicleDTO.getAgencyId())
                .orElseThrow(() -> new EntityNotFoundException("Agence non trouvée"));

        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicleRepository.save(vehicle);
    }

    public void safeDeleteVehicle(Long id) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            Vehicle vehicle = vehicleOptional.get();
            vehicle.setDeleted(true);
            vehicleRepository.save(vehicle);
        }
    }

    public void updateVehicle(Long id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id:" + id));

        VehicleType vehicleType = vehicleTypeRepository.findById(vehicleDTO.getVehicleTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Vehicle type not found with id:" + vehicleDTO.getVehicleTypeId()));

        vehicle.setType(vehicleType);
        vehicle.setStatus(VehicleStatus.valueOf(vehicleDTO.getStatus()));
        vehicle.setEndInsurance(vehicleDTO.getEndInsurance());
        vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicle.setEndTechnicalControl(vehicleDTO.getEndTechnicalControl());
        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
