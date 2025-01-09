package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.domain.enums.ReservationStatus;
import com.bld.parc_oto_back.dto.ReservationDTO;
import com.bld.parc_oto_back.infrastructure.ReservationRepository;
import com.bld.parc_oto_back.infrastructure.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public Optional<ReservationDTO> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDto);
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
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
