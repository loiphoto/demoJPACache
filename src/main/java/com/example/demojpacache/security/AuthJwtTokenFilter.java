package com.example.demojpacache.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthJwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("~~~~~~~~~Go filter ~~~~~~~~");
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Strings.isEmpty(header) || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.split(" ")[1].trim();
            if (!jwtTokenUtil.validateJwtToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            UserSercurityImpl user = (UserSercurityImpl) jwtTokenUtil.parseToken(token);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            logger.error("Cannot set user authentication: {}", ex);
            throw new AccessDeniedException("Unauthorized");
        }
    }
}
