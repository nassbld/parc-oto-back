package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.auth.AuthenticationService;
import com.bld.parc_oto_back.application.auth.JwtService;
import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.dto.UserDTO;
import com.bld.parc_oto_back.dto.auth.AuthenticationRequest;
import com.bld.parc_oto_back.dto.auth.AuthenticationResponse;
import com.bld.parc_oto_back.infrastructure.UserRepository;
import com.bld.parc_oto_back.infrastructure.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/verify-token")
    public ResponseEntity<String> verifyToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body("Token manquant ou invalide");
        }

        String token = authHeader.substring(7);
        try {
            String username = jwtService.extractUsername(token);
            System.out.println("Nom d'utilisateur extrait : " + username);
            return ResponseEntity.ok("Token valide");
        } catch (Exception e) {
            System.err.println("Erreur lors de la validation du token : " + e.getMessage());
            return ResponseEntity.status(403).body("Token invalide");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        User user = authenticationService.getCurrentAuthenticatedUser(authentication);
        UserDTO userDTO = userMapper.toDto(user);

        return ResponseEntity.ok(userDTO);
    }

}
