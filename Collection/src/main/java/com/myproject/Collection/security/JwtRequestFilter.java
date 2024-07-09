package com.myproject.Collection.security;

import com.myproject.Collection.dto.LoginRequestDTO;
import com.myproject.Collection.service.JWTServiceImplement;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTServiceImplement jwtService;

    public JwtRequestFilter(UserDetailsService userDetailsService, JWTServiceImplement jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        try {
            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

            final String authorizationHeader = request.getHeader("Authorization");

            String username = null;
            String jwtToken = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);
                //username = jwtService.generateToken(jwtToken); // Implement this method in JWTServiceImplement
                System.out.println("jwtTokennnnnnnnnnnnnnnnn:   " + jwtToken);
                username = jwtService.resolveToken(jwtToken);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            chain.doFilter(request, response);

        }catch (IOException e) {
            System.out.println("tttttttttttttttt       " + e);
            throw new RuntimeException(e);
        } catch (ServletException e) {
            System.out.println("tttttttttttttttt       " + e);
            throw new RuntimeException(e);
        }

    }

}
