package com.bld.parc_oto_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgencyDTO {
    private Long id;
    private String name;
    private String street;
    private String department;
    private String postalCode;
    private String city;
    private String country;
    private List<Long> vehicleIds;
}
