package com.bld.parc_oto_back.infrastructure;

import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.domain.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByStatus(VehicleStatus status);
}
