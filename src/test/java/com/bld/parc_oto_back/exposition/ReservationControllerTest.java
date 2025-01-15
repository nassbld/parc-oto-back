package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.ReservationService;
import com.bld.parc_oto_back.domain.Reservation;
import com.bld.parc_oto_back.domain.enums.ReservationStatus;
import com.bld.parc_oto_back.dto.ReservationDTO;
import com.bld.parc_oto_back.infrastructure.mapper.ReservationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ReservationMapper reservationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllReservations_shouldReturnListOfReservations() throws Exception {
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        ReservationDTO reservationDTO1 = reservationMapper.toDto(reservation1);
        ReservationDTO reservationDTO2 = reservationMapper.toDto(reservation2);
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservationDTO1, reservationDTO2));

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getReservationById_shouldReturnReservation_whenReservationExists() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservationDTO));

        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getReservationById_shouldReturnNotFound_whenReservationDoesNotExist() throws Exception {
        when(reservationService.getReservationById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReservation_shouldReturnNoContent() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setStart(LocalDateTime.now());
        reservation.setEnd(LocalDateTime.now().plusDays(1));

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isNoContent());

        verify(reservationService, times(1)).createReservation(any(ReservationDTO.class));
    }

    @Test
    void updateReservationStatus_shouldReturnNoContent() throws Exception {
        mockMvc.perform(put("/reservations/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(ReservationStatus.CONFIRMED)))
                .andExpect(status().isNoContent());

        verify(reservationService, times(1)).updateReservationStatus(eq(1L), eq("CONFIRMED"));
    }

    @Test
    void deleteReservation_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isNoContent());

        verify(reservationService, times(1)).deleteReservation(1L);
    }
}
