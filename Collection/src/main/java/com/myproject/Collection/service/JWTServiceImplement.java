package com.myproject.Collection.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myproject.Collection.controller.LoginController;
import com.myproject.Collection.dto.LoginRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
@Component
public class JWTServiceImplement implements JWTService{
    private static final Logger logger = LoggerFactory.getLogger(JWTServiceImplement.class);
    private final String ALGORITHM_KEY = "ALGORITHMKEY";

    public String generateToken(String name) {
        try{
            // Set an expiration time of 1 hour
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, 1); // Expiration time is 1 hour

            // Generate the JWT token
            String token =JWT.create()
                    .withHeader(new HashMap<>()) // Header
                    .withClaim("username", name) // Payload
                    .withExpiresAt(calendar.getTime()) // Expiration time
                    .sign(Algorithm.HMAC256(ALGORITHM_KEY)); // Signing key (secret)

            // Print the generated token
            return token;
        }catch (JWTCreationException myJWTCreationException){
            throw myJWTCreationException;
        }
    }


    public String resolveToken(String token) {


        try{
            // Create the verifier using the same algorithm and secret as for token creation
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(ALGORITHM_KEY)).build();
            //JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("test")).build(); // test for SignatureVerificationException exception
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim userName = decodedJWT.getClaim("username");


            return userName.asString();
        }catch (JWTDecodeException exception){
            logger.error("JWT Decode exception: {}", exception);
            throw exception;
        }catch (SignatureVerificationException exception){
            logger.error("JWT signature verification fail: {}", exception);
            throw exception;
        }catch (JWTVerificationException exception){
            logger.error("JWT verification fail: {}", exception);
            throw exception;
        }

    }

}
