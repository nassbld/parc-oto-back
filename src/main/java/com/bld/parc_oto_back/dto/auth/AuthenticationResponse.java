package com.bld.parc_oto_back.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String email;
    private String first_name;
    private String last_name;
    private String matricule;
    private String phone;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}