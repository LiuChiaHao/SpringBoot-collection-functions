package com.myproject.Collection.controller;

import com.myproject.Collection.dto.LoginRequestDTO;
import com.myproject.Collection.entity.StaffEntity;
import com.myproject.Collection.service.JWTServiceImplement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/JWT")
public class JWTController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private JWTServiceImplement theJWTServiceImplement;


    @GetMapping("/generateToken")
    public String generateToken(Model theModel){
        logger.info("Showing generate token");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        theModel.addAttribute("createToken", theJWTServiceImplement.generateToken(username));

        return "JWT-page";
    }


}
