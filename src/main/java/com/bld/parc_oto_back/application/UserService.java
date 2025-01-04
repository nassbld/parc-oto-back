package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
