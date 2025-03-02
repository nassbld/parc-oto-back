package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.VehicleService;
import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.dto.VehicleDTO;
import com.bld.parc_oto_back.dto.VehicleIdentityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/identity/{id}")
    public ResponseEntity<VehicleIdentityDTO> getVehicleIdentityById(@PathVariable Long id) {
        return vehicleService.getVehicleIdentityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/agency/{id}")
    public ResponseEntity<List<VehicleDTO>> getVehiclesByAgencyId(@PathVariable Long id) {
        List<VehicleDTO> vehicles = vehicleService.getVehiclesByAgencyId(id);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/identity/agency/{id}")
    public ResponseEntity<List<VehicleIdentityDTO>> getVehiclesIdentityByAgencyId(@PathVariable Long id) {
        List<VehicleIdentityDTO> vehicles = vehicleService.getVehiclesIdentityByAgencyId(id);
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<Void> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        vehicleService.addVehicle(vehicleDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
        vehicleService.updateVehicle(id, vehicleDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/safe-delete/{id}")
    public ResponseEntity<Void> safeDeleteVehicle(@PathVariable Long id) {
        vehicleService.safeDeleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}