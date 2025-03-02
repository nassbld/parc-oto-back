package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.VehicleTypeService;
import com.bld.parc_oto_back.dto.VehicleTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle-types")
public class VehicleTypeController {

    private final VehicleTypeService vehicleTypeService;

    @Autowired
    public VehicleTypeController(VehicleTypeService vehicleTypeService) {
        this.vehicleTypeService = vehicleTypeService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleTypeDTO>> getAllVehicleTypes() {
        List<VehicleTypeDTO> vehicleTypes = vehicleTypeService.getAllVehicleTypes();
        return ResponseEntity.ok(vehicleTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleTypeDTO> getVehicleTypeById(@PathVariable Long id) {
        return vehicleTypeService.getVehicleTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brand")
    public ResponseEntity<List<VehicleTypeDTO>> getVehicleTypeByBrand(@RequestParam String brand) {
        List<VehicleTypeDTO> vehicleTypes = vehicleTypeService.getVehicleTypeByBrand(brand);
        return ResponseEntity.ok(vehicleTypes);
    }

    @PostMapping
    public ResponseEntity<VehicleTypeDTO> createVehicleType(@RequestBody VehicleTypeDTO vehicleTypeDTO) {
        VehicleTypeDTO createdVehicleType = vehicleTypeService.createVehicleType(vehicleTypeDTO);
        return ResponseEntity.ok(createdVehicleType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleTypeDTO> updateVehicleType(@PathVariable Long id, @RequestBody VehicleTypeDTO vehicleTypeDTO) {
        vehicleTypeService.updateVehicleType(id, vehicleTypeDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleType(@PathVariable Long id) {
        vehicleTypeService.deleteVehicleType(id);
        return ResponseEntity.noContent().build();
    }
}
