package com.bld.parc_oto_back.infrastructure;

import com.bld.parc_oto_back.domain.User;
import com.bld.parc_oto_back.dto.AgencyListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<Object> findByMatricule(String matricule);
    Optional<List<Long>> findFavoriteAgenciesIdsById(Long id);
}
