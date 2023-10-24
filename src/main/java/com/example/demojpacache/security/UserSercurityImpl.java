package com.example.demojpacache.security;

import com.example.demojpacache.Entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class UserSercurityImpl implements UserDetails {

    private final Long id;

    private final String username;

    private final String password;

    private final Role role;

    private final String email;

    public UserSercurityImpl(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = Role.findRoleByName(user.getRole().getName());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getSimpleGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
