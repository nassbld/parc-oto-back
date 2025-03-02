package com.bld.parc_oto_back.infrastructure;

import com.bld.parc_oto_back.domain.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    List<VehicleType> findByBrand(String brand);
}
