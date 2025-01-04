package com.bld.parc_oto_back.infrastructure;

import com.bld.parc_oto_back.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReservationId(Long reservationId);
}
