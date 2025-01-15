package com.bld.parc_oto_back.domain;

import com.bld.parc_oto_back.domain.enums.ReservationStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void testReservationCreation() {
        User user = new User();
        Vehicle vehicle = new Vehicle();
        LocalDateTime debut = LocalDateTime.now();
        LocalDateTime fin = debut.plusDays(1);
        List<Report> reports = new ArrayList<>();

        Reservation reservation = new Reservation(1L, user, vehicle, debut, fin, ReservationStatus.PENDING, reports);

        assertEquals(1L, reservation.getId());
        assertEquals(user, reservation.getUser());
        assertEquals(vehicle, reservation.getVehicle());
        assertEquals(debut, reservation.getStart());
        assertEquals(fin, reservation.getEnd());
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
        assertEquals(reports, reservation.getReports());
    }

    @Test
    void testNoArgsConstructor() {
        Reservation reservation = new Reservation();

        assertNull(reservation.getId());
        assertNull(reservation.getUser());
        assertNull(reservation.getVehicle());
        assertNull(reservation.getStart());
        assertNull(reservation.getEnd());
        assertNull(reservation.getStatus());
        assertNull(reservation.getReports());
    }

    @Test
    void testSettersAndGetters() {
        Reservation reservation = new Reservation();
        User user = new User();
        Vehicle vehicle = new Vehicle();
        LocalDateTime debut = LocalDateTime.now();
        LocalDateTime fin = debut.plusDays(2);
        List<Report> reports = new ArrayList<>();

        reservation.setId(2L);
        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        reservation.setStart(debut);
        reservation.setEnd(fin);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setReports(reports);

        assertEquals(2L, reservation.getId());
        assertEquals(user, reservation.getUser());
        assertEquals(vehicle, reservation.getVehicle());
        assertEquals(debut, reservation.getStart());
        assertEquals(fin, reservation.getEnd());
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
        assertEquals(reports, reservation.getReports());
    }
}
