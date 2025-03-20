package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.UserService;
import com.bld.parc_oto_back.domain.Agency;
import com.bld.parc_oto_back.dto.AgencyDTO;
import com.bld.parc_oto_back.dto.AgencyListDTO;
import com.bld.parc_oto_back.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/favorite-agencies")
    public ResponseEntity<AgencyListDTO> getFavoriteAgencies(@PathVariable Long userId) {
        AgencyListDTO favoriteAgencies = userService.getFavoriteAgencies(userId);
        return ResponseEntity.ok(favoriteAgencies);
    }

    @PutMapping("/{userId}/favorite-agencies")
    public ResponseEntity<Void> setFavoriteAgencies(@PathVariable Long userId, @RequestBody AgencyListDTO agencyListDTO) {
        userService.setFavoriteAgencies(userId, agencyListDTO.getAgencyIds());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}