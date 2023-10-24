package com.example.demojpacache.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.demojpacache.security.Permission.*;

@Getter
@AllArgsConstructor
public enum Role {

    USER(Arrays.asList(USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE)),
    ADMIN(Arrays.asList(ADMIN_CREATE, ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE,
            USER_CREATE, USER_READ, USER_UPDATE, USER_DELETE)),
    ;

    public final List<Permission> permissions;

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.permissions));
        return authorities;
    }

    public static Role findRoleByName(String name) {
        return Arrays.stream(values()).filter(role -> role.name().toUpperCase().equals(name)).findFirst().orElse(null);
    }


}
