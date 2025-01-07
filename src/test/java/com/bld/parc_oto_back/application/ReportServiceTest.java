package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Report;
import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.domain.enums.ReportType;
import com.bld.parc_oto_back.infrastructure.ReportRepository;
import com.bld.parc_oto_back.infrastructure.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReportById_shouldReturnReport_whenReportExists() {
        Report report = new Report();
        report.setId(1L);
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Optional<Report> result = reportService.getReportById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getAllReports_shouldReturnListOfReports() {
        List<Report> reports = Arrays.asList(new Report(), new Report());
        when(reportRepository.findAll()).thenReturn(reports);

        List<Report> result = reportService.getAllReports();

        assertEquals(2, result.size());
    }

    @Test
    void createReport_shouldSaveReport_whenValidReservationIdProvided() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        Report report = new Report();
        report.setDescription("Test Report");
        report.setType(ReportType.DAMAGE);
        report.setReportDateTime(LocalDateTime.now());

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reportService.createReport(reservationId, report);

        verify(reservationRepository).findById(reservationId);
        verify(reportRepository).save(report);
        assertEquals(reservation, report.getReservation());
    }

    @Test
    void createReport_shouldThrowException_whenReservationNotFound() {
        Long reservationId = 1L;
        Report report = new Report();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reportService.createReport(reservationId, report));

        verify(reservationRepository).findById(reservationId);
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    void deleteReport_shouldDeleteReport() {
        Long reportId = 1L;

        reportService.deleteReport(reportId);

        verify(reportRepository).deleteById(reportId);
    }
}
