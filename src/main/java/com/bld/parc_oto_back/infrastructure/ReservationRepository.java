package com.bld.parc_oto_back.infrastructure;

import com.bld.parc_oto_back.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByVehicleId(Long vehicleId);

    @Query("SELECT r FROM Reservation r WHERE r.vehicle.agency.id = :agencyId")
    List<Reservation> findByAgencyId(@Param("agencyId") Long agencyId);
}
