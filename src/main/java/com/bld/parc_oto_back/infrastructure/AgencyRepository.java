package com.bld.parc_oto_back.infrastructure;

import com.bld.parc_oto_back.domain.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Agency findByName(String name);
}
