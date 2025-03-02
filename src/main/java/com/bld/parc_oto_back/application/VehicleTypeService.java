package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.VehicleType;
import com.bld.parc_oto_back.dto.VehicleTypeDTO;
import com.bld.parc_oto_back.infrastructure.VehicleTypeRepository;
import com.bld.parc_oto_back.infrastructure.mapper.VehicleTypeMapper;
import io.micrometer.observation.ObservationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleTypeService {

    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleTypeMapper vehicleTypeMapper;

    @Autowired
    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository, VehicleTypeMapper vehicleTypeMapper) {
        this.vehicleTypeRepository = vehicleTypeRepository;
        this.vehicleTypeMapper = vehicleTypeMapper;
    }

    public List<VehicleTypeDTO> getAllVehicleTypes() {
        return vehicleTypeRepository.findAll().stream()
                .map(vehicleTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<VehicleTypeDTO> getVehicleTypeByBrand(String brand) {
        return vehicleTypeRepository.findByBrand(brand).stream()
                .map(vehicleTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<VehicleTypeDTO> getVehicleTypeById(Long id) {
        return vehicleTypeRepository.findById(id)
                .map(vehicleTypeMapper::toDTO);
    }

    public VehicleTypeDTO createVehicleType(VehicleTypeDTO vehicleTypeDTO) {
        VehicleType vehicleType = vehicleTypeMapper.toEntity(vehicleTypeDTO);
        VehicleType savedVehicleType = vehicleTypeRepository.save(vehicleType);
        return vehicleTypeMapper.toDTO(savedVehicleType);
    }

    public VehicleTypeDTO updateVehicleType(Long id, VehicleTypeDTO vehicleTypeDTO) {
        VehicleType existingVehicleType = vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VehicleType not found with id:" + id));

        existingVehicleType.setBrand(vehicleTypeDTO.getBrand());
        existingVehicleType.setModel(vehicleTypeDTO.getModel());
        existingVehicleType.setImageUrl(vehicleTypeDTO.getImageUrl());

        VehicleType updatedVehicleType = vehicleTypeRepository.save(existingVehicleType);
        return vehicleTypeMapper.toDTO(updatedVehicleType);
    }

    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteById(id);
    }
}
