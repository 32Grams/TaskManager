package ru.semenov.itpower.taskmanager.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsername(jwtToken);
            } catch (Exception e) {
                // TODO: 03.09.2024 Обработать исключение.
            }
        }

        if( username!= null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(!jwtTokenUtil.isTokenExpired(jwtToken)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //validate token
                try {
                    Claims claims = jwtTokenUtil.getClaims(jwtToken);
                    if(userDetails.getUsername().equals(claims.getSubject())) {
                        Authentication authentication = jwtTokenUtil.getAuthentication(jwtToken, userDetails);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (Exception e) {
                    // TODO: 03.09.2024 Обработать исключение.
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
