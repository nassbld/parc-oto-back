package com.bld.parc_oto_back.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricule;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "user_favorite_agencies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "agency_id")
    )
    private List<Agency> favoriteAgencies = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Agency> getFavoriteAgencies() {
        if (favoriteAgencies == null) {
            favoriteAgencies = new ArrayList<>();
        }
        return favoriteAgencies;
    }

    public void addFavoriteAgency(Agency agency) {
        if (favoriteAgencies == null) {
            favoriteAgencies = new ArrayList<>();
        }
        favoriteAgencies.add(agency);
    }

    public void removeFavoriteAgency(Agency agency) {
        if (favoriteAgencies != null) {
            favoriteAgencies.remove(agency);
        }
    }
}
