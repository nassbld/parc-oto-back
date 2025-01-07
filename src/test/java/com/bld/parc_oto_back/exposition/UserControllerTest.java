package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.UserService;
import com.bld.parc_oto_back.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUserById_shouldReturnUser_whenUserExists() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setMatricule("M123");
        user.setLast_name("Doe");
        user.setFirst_name("John");
        user.setEmail("john.doe@example.com");
        user.setPhone("1234567890");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.matricule").value("M123"))
                .andExpect(jsonPath("$.last_name").value("Doe"))
                .andExpect(jsonPath("$.first_name").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phone").value("1234567890"));
    }

    @Test
    void getUserById_shouldReturnNotFound_whenUserDoesNotExist() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_shouldReturnCreatedUser() throws Exception {
        User user = new User();
        user.setMatricule("M456");
        user.setLast_name("Smith");
        user.setFirst_name("Jane");
        user.setEmail("jane.smith@example.com");
        user.setPhone("0987654321");

        User createdUser = new User();
        createdUser.setId(2L);
        createdUser.setMatricule("M456");
        createdUser.setLast_name("Smith");
        createdUser.setFirst_name("Jane");
        createdUser.setEmail("jane.smith@example.com");
        createdUser.setPhone("0987654321");

        when(userService.createUser(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.matricule").value("M456"))
                .andExpect(jsonPath("$.last_name").value("Smith"))
                .andExpect(jsonPath("$.first_name").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.phone").value("0987654321"));
    }

    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}
