package com.myproject.Collection.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myproject.Collection.dto.LoginRequestDTO;
import org.springframework.stereotype.Component;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.HashMap;
@Component
public class JWTServiceImplement implements JWTService{

    private final String ALGORITHM_KEY = "ALGORITHMKEY";

    public String generateToken(/*LoginRequestDTO loginRequestDTO*/ String name) {
        // Set an expiration time of 1 hour
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1); // Expiration time is 1 hour

        // Generate the JWT token
        String token = JWT.create()
                .withHeader(new HashMap<>()) // Header
                .withClaim("username", name) // Payload
                .withExpiresAt(calendar.getTime()) // Expiration time
                .sign(Algorithm.HMAC256(ALGORITHM_KEY)); // Signing key (secret)

        // Print the generated token
        System.out.println(token);
        return token;
    }


    public String resolveToken(String token) {

        System.out.println("token:  " + token);
       /* System.out.println("loginRequestDTO:  " + loginRequestDTO.getUsername());
        System.out.println("loginRequestDTO:  " + loginRequestDTO.getPassword());
*/
       // try{
            // Create the verifier using the same algorithm and secret as for token creation
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(ALGORITHM_KEY)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Claim userName = decodedJWT.getClaim("username");


            // Decode the token and extract the claims
           /* Claim userName = decodedJWT.getClaim("username");

            // Print the claims
            System.out.println("userName: " + userName.asString());

            // Convert and print the expiration time
            Instant instant = decodedJWT.getExpiresAt().toInstant();
            LocalDateTime expirationTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            System.out.println("Expiration Time: " + expirationTime);
*/
            return userName.asString();
       // }catch (JWTVerificationException exception){
            //return false;
       // }



    }

}
