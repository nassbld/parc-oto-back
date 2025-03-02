package com.bld.parc_oto_back.application.auth;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String generatedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("Nouvelle clé JWT : " + generatedKey);
    }
}
