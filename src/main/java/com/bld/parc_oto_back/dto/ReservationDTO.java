package com.bld.parc_oto_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Long vehicleId;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private String status;
    private List<Long> reportIds;
}
