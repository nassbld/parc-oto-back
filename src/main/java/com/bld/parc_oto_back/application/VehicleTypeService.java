package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.VehicleType;
import com.bld.parc_oto_back.dto.VehicleTypeDTO;
import com.bld.parc_oto_back.infrastructure.VehicleTypeRepository;
import com.bld.parc_oto_back.infrastructure.mapper.VehicleTypeMapper;
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

    public Optional<VehicleTypeDTO> getVehicleTypeById(Long id) {
        return vehicleTypeRepository.findById(id)
                .map(vehicleTypeMapper::toDTO);
    }

    public VehicleTypeDTO createVehicleType(VehicleTypeDTO vehicleTypeDTO) {
        VehicleType vehicleType = vehicleTypeMapper.toEntity(vehicleTypeDTO);
        VehicleType savedVehicleType = vehicleTypeRepository.save(vehicleType);
        return vehicleTypeMapper.toDTO(savedVehicleType);
    }

    public Optional<VehicleTypeDTO> updateVehicleType(Long id, VehicleTypeDTO vehicleTypeDTO) {
        return vehicleTypeRepository.findById(id)
                .map(existingVehicleType -> {
                    VehicleType updatedVehicleType = vehicleTypeMapper.toEntity(vehicleTypeDTO);
                    updatedVehicleType.setId(existingVehicleType.getId());
                    return vehicleTypeMapper.toDTO(vehicleTypeRepository.save(updatedVehicleType));
                });
    }

    public void deleteVehicleType(Long id) {
        vehicleTypeRepository.deleteById(id);
    }
}
