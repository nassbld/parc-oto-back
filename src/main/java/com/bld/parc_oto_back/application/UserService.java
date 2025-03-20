package com.bld.parc_oto_back.application;

import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.dto.AgencyListDTO;
import com.bld.parc_oto_back.dto.UserDTO;
import com.bld.parc_oto_back.infrastructure.AgencyRepository;
import com.bld.parc_oto_back.infrastructure.UserRepository;
import com.bld.parc_oto_back.infrastructure.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AgencyRepository agencyRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, AgencyRepository agencyRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.agencyRepository = agencyRepository;
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AgencyListDTO getFavoriteAgencies(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        return userMapper.toAgencyListDTO(user);
    }

    @Transactional
    public void setFavoriteAgencies(Long userId, List<Long> agencyIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        List<Agency> agencies = agencyRepository.findAllById(agencyIds);
        user.setFavoriteAgencies(agencies);

        userRepository.save(user);
    }
}
