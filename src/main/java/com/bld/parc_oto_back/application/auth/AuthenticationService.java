package com.bld.parc_oto_back.application.auth;

import com.bld.parc_oto_back.domain.exception.UserAlreadyExistsException;
import com.bld.parc_oto_back.dto.auth.AuthenticationRequest;
import com.bld.parc_oto_back.dto.auth.AuthenticationResponse;
import com.bld.parc_oto_back.infrastructure.UserRepository;
import com.bld.parc_oto_back.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse register(AuthenticationRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("email", request.getEmail());
        }

        if (userRepository.findByMatricule(request.getMatricule()).isPresent()) {
            throw new UserAlreadyExistsException("matricule", request.getMatricule());
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .matricule(request.getMatricule())
                .phone(request.getPhone())
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .matricule(user.getMatricule())
                .phone(user.getPhone())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .matricule(user.getMatricule())
                .phone(user.getPhone())
                .build();
    }

    public User getCurrentAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }
}


