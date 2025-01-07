package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.domain.enums.ReservationStatus;
import com.bld.parc_oto_back.infrastructure.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) {
        reservationRepository.save(reservation);
        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public void updateReservationStatus(Long id, String status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + id));
        reservation.setStatus(ReservationStatus.valueOf(status));
        reservationRepository.save(reservation);
    }


}
