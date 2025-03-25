package com.inglo.giggle.security.info;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
public class CustomUserPrincipal implements UserDetails {

    @Getter private final UUID id;
    @Getter private final ESecurityRole role;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static CustomUserPrincipal create(Account account) {
        return CustomUserPrincipal.builder()
                .id(account.getId())
                .role(account.getRole())
                .password(account.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(account.getRole().getSecurityName())))
                .build();
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
}

