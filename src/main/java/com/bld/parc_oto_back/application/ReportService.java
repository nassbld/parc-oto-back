package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Report;
import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.infrastructure.ReportRepository;
import com.bld.parc_oto_back.infrastructure.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReservationRepository reservationRepository;

    public ReportService(ReportRepository reportRepository, ReservationRepository reservationRepository) {
        this.reportRepository = reportRepository;
        this.reservationRepository = reservationRepository;
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public void createReport(Long reservationId, Report report) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + reservationId));

        report.setReservation(reservation);
        reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}
