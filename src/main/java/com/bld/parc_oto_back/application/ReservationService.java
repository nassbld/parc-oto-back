package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.domain.Vehicle;
import com.bld.parc_oto_back.domain.enums.ReservationStatus;
import com.bld.parc_oto_back.dto.ReservationDTO;
import com.bld.parc_oto_back.dto.ReservationWithInfosDTO;
import com.bld.parc_oto_back.dto.UserDTO;
import com.bld.parc_oto_back.dto.VehicleIdentityDTO;
import com.bld.parc_oto_back.infrastructure.ReservationRepository;
import com.bld.parc_oto_back.infrastructure.UserRepository;
import com.bld.parc_oto_back.infrastructure.VehicleRepository;
import com.bld.parc_oto_back.infrastructure.mapper.ReservationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    private final VehicleService vehicleService;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationMapper reservationMapper,
                              VehicleService vehicleService,
                              UserService userService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    public Optional<ReservationDTO> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(reservationMapper::toDto);
    }

    public List<ReservationDTO> getReservationsByAgencyId(Long agencyId) {
        return reservationRepository.findByAgencyId(agencyId).stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<ReservationWithInfosDTO> getReservationsWithInformationsByAgencyId(Long agencyId) {
        List<ReservationDTO> reservationsDTO = reservationRepository.findByAgencyId(agencyId).stream()
                .map(reservationMapper::toDto)
                .toList();

        return reservationsDTO.stream()
                .map(reservationDTO -> {
                    UserDTO userDTO = userService.getUserById(reservationDTO.getUserId())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    VehicleIdentityDTO vehicleIdentityDTO = vehicleService.getVehicleIdentityByIdWithDeleted(reservationDTO.getVehicleId())
                            .orElseThrow(() -> new RuntimeException("Vehicle not found"));

                    return getReservationWithInfosDTO(reservationDTO, userDTO, vehicleIdentityDTO);
                })
                .collect(Collectors.toList());
    }

    private static ReservationWithInfosDTO getReservationWithInfosDTO(ReservationDTO reservationDTO, UserDTO userDTO, VehicleIdentityDTO vehicleIdentityDTO) {
        ReservationWithInfosDTO dto = new ReservationWithInfosDTO();
        dto.setId(reservationDTO.getId());
        dto.setStart(reservationDTO.getStart());
        dto.setEnd(reservationDTO.getEnd());
        dto.setStatus(reservationDTO.getStatus());
        dto.setUserFirstName(userDTO.getFirstName());
        dto.setUserLastName(userDTO.getLastName());
        dto.setUserMatricule(userDTO.getMatricule());
        dto.setVehicleBrand(vehicleIdentityDTO.getBrand());
        dto.setVehicleModel(vehicleIdentityDTO.getModel());
        dto.setVehicleLicensePlate(vehicleIdentityDTO.getLicensePlate());
        return dto;
    }

    public List<ReservationDTO> getTodayReservationsByAgencyId(Long agencyId) {
        return reservationRepository.findTodayByAgencyId(agencyId).stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
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
