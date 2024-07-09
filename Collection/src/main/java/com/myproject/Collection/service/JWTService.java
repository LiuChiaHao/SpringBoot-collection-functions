package com.myproject.Collection.service;

import com.myproject.Collection.dto.LoginRequestDTO;

public interface JWTService {
    String generateToken(String name);
    String resolveToken(String token);
}
