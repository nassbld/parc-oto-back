package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.dto.UserDTO;
import com.bld.parc_oto_back.infrastructure.UserRepository;
import com.bld.parc_oto_back.infrastructure.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    void createUser_shouldSaveAndReturnUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.save(user)).thenReturn(user);

        UserDTO userDTO = userMapper.toDto(user);

        UserDTO result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }
}
