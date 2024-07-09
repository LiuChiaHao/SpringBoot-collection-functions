package com.myproject.Collection.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @GetMapping("/")
    public String redirectToLoginPage() {
        logger.info("Redirecting to login page");
        return "redirect:/loginPage";
    }
    @GetMapping("/loginPage")
    public String showMyLoginPage(){
        logger.info("Login page");
        return "login-page";
    }

}
