package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.domain.enums.ReservationStatus;
import com.bld.parc_oto_back.infrastructure.ReservationRepository;
import com.bld.parc_oto_back.infrastructure.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void testGetReservationById_Success() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.getReservationById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllReservations_Success() {
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationService.getAllReservations();

        assertEquals(2, result.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testCreateReservation_Success() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(ReservationStatus.PENDING);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation result = reservationService.createReservation(reservation);

        assertNotNull(result, "Reservation should not be null");
        assertEquals(ReservationStatus.PENDING, result.getStatus(), "Reservation status should be PENDING");
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testDeleteReservation_Success() {
        Long reservationId = 1L;

        reservationService.deleteReservation(reservationId);

        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    public void testUpdateReservationStatus_Success() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(ReservationStatus.PENDING);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        reservationService.updateReservationStatus(1L, ReservationStatus.CONFIRMED.name());

        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus(), "Reservation status should be CONFIRMED");
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(argThat(r -> r.getStatus() == ReservationStatus.CONFIRMED));
    }

    @Test
    public void testUpdateReservationStatus_NotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.updateReservationStatus(1L, ReservationStatus.CONFIRMED.name())
        );

        assertEquals("Reservation not found with id: 1", exception.getMessage());
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}
