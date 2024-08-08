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

    //UserDetailsService will get the login username
    private final UserDetailsService userDetailsService;
    private final JWTServiceImplement myJWTServiceImplement;

    // initial the user detail and
    public JwtRequestFilter(UserDetailsService userDetailsService, JWTServiceImplement myJWTServiceImplement) {
        this.userDetailsService = userDetailsService;
        this.myJWTServiceImplement = myJWTServiceImplement;
    }

    // make sure the token is right and then login
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {

        try {
            // get the header Authorization
            final String authorizationHeader = request.getHeader("Authorization");

            String username = null;
            String jwtToken = null;

            //resolve to get token, and then resolve to get username
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);
                username = myJWTServiceImplement.resolveToken(jwtToken);
            }

            // check if the user is already authentize
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //load the user detail using uername
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //Creates a new UsernamePasswordAuthenticationToken with the user details and authorities.
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //Adds additional request details to the authentication token.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Sets the authentication token in the SecurityContextHolder, marking the user as authenticated.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            //pass the filter request to the MVC
            chain.doFilter(request, response);

        }catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }

}
