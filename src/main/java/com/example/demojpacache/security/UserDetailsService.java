package com.example.demojpacache.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {

    UserDetails loadUserByUserName(String userName) throws UsernameNotFoundException;
}
