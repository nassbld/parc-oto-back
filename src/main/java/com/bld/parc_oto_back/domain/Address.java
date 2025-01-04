package com.bld.parc_oto_back.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private String street;
    private String postalCode;
    private String city;
    private String country;
}
