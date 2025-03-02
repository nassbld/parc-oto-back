package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.dto.auth.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    void registerShouldCreateNewUser() throws Exception {
//        AuthenticationRequest request = new AuthenticationRequest(
//                "test123@email.com",
//                "password123",
//                "John",
//                "Doe",
//                "MAT123",
//                "0123456789"
//        );
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(request.getEmail()));
//    }

    @Test
    void loginShouldAuthenticateUser() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(
                "test123@email.com",
                "password123",
                null,
                null,
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }
}

