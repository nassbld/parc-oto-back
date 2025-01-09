package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Report;
import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.dto.ReportDTO;
import com.bld.parc_oto_back.infrastructure.ReportRepository;
import com.bld.parc_oto_back.infrastructure.ReservationRepository;
import com.bld.parc_oto_back.infrastructure.mapper.ReportMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReservationRepository reservationRepository;
    private final ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository, ReservationRepository reservationRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.reservationRepository = reservationRepository;
        this.reportMapper = reportMapper;
    }

    public Optional<ReportDTO> getReportById(Long id) {
        return reportRepository.findById(id)
                .map(reportMapper::toDto);
    }

    public List<ReportDTO> getAllReports() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toDto)
                .collect(Collectors.toList());
    }

    public void createReport(Long reservationId, ReportDTO reportDTO) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + reservationId));

        Report report = reportMapper.toEntity(reportDTO);
        report.setReservation(reservation);
        reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}
