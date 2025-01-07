package com.bld.parc_oto_back.domain;

import com.bld.parc_oto_back.domain.enums.ReportType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void testReportCreation() {
        Reservation reservation = new Reservation(); // Assurez-vous que cette réservation est correctement instanciée
        reservation.setId(1L); // Exemple d'ID pour la réservation

        Report report = new Report();
        report.setId(1L);
        report.setReservation(reservation);
        report.setDescription("Test Report");
        report.setType(ReportType.DAMAGE);
        report.setReportDateTime(LocalDateTime.now());

        assertEquals(1L, report.getId());
        assertEquals(reservation, report.getReservation());
        assertEquals("Test Report", report.getDescription());
        assertEquals(ReportType.DAMAGE, report.getType());
    }

    @Test
    void testNoArgsConstructor() {
        Report report = new Report();

        assertNull(report.getId());
        assertNull(report.getReservation());
        assertNull(report.getDescription());
        assertNull(report.getType());
        assertNull(report.getReportDateTime());
    }

    @Test
    void testSettersAndGetters() {
        Report report = new Report();
        Reservation reservation = new Reservation();
        reservation.setId(2L); // Exemple d'ID pour la réservation

        report.setId(3L);
        report.setReservation(reservation);
        report.setDescription("Another Test Report");
        report.setType(ReportType.LATE_RETURN);
        report.setReportDateTime(LocalDateTime.now());

        assertEquals(3L, report.getId());
        assertEquals(reservation, report.getReservation());
        assertEquals("Another Test Report", report.getDescription());
        assertEquals(ReportType.LATE_RETURN, report.getType());
    }
}
