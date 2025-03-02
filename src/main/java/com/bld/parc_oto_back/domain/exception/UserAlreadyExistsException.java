package com.bld.parc_oto_back.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String field, String value) {
        super(String.format("Un utilisateur avec %s '%s' existe déjà", field, value));
    }
}

