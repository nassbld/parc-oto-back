package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.AgencyService;
import com.bld.parc_oto_back.domain.Agency;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agency> getAgencyById(@PathVariable Long id) {
        return agencyService.getAgencyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createAgency(@RequestBody Agency agency) {
        agencyService.createAgency(agency);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAgency(@PathVariable Long id, @RequestBody Agency agency) {
        if (!id.equals(agency.getId())) {
            return ResponseEntity.badRequest().build();
        }

        agencyService.updateAgency(agency);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        agencyService.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }
}
