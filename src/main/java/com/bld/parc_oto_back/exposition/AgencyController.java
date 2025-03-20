package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.AgencyService;
import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.dto.AgencyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping
    public ResponseEntity<List<AgencyDTO>> getAllAgencies() {
        List<AgencyDTO> agencies = agencyService.getAllAgencies();
        return ResponseEntity.ok(agencies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyDTO> getAgencyById(@PathVariable Long id) {
        return agencyService.getAgencyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/by-ids")
    public ResponseEntity<List<AgencyDTO>> getAgenciesByIds(@RequestBody List<Long> agencyIds) {
        List<AgencyDTO> agencies = agencyService.getAgenciesByIds(agencyIds);
        return ResponseEntity.ok(agencies);
    }

    @PostMapping
    public ResponseEntity<Void> createAgency(@RequestBody AgencyDTO agencyDTO) {
        agencyService.createAgency(agencyDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAgency(@PathVariable Long id, @RequestBody AgencyDTO agencyDTO) {
        if (!id.equals(agencyDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        agencyService.updateAgency(agencyDTO);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        agencyService.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }
}