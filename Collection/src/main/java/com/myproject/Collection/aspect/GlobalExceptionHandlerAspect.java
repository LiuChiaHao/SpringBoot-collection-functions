package com.myproject.Collection.aspect;

import com.auth0.jwt.exceptions.*;
import com.myproject.Collection.controller.LoginController;
import com.myproject.Collection.service.JWTServiceImplement;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.security.SignatureException;

@Aspect
@Component
public class GlobalExceptionHandlerAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final Logger JWTServiceImplementLogger = LoggerFactory.getLogger(JWTServiceImplement.class);

    // EntityNotFoundException
    @AfterThrowing(pointcut = "com.myproject.Collection.aspect.ExceptionPath.forEntityNotFoundException()", throwing = "ex")
    public void handleEntityNotFoundException(JoinPoint theJoinPoint,EntityNotFoundException ex) {
        String method = theJoinPoint.getSignature().toShortString();
        logger.error("Exception in method {}: {}", method, ex.getMessage());
    }

    // NotValidException
    @AfterThrowing(pointcut = "com.myproject.Collection.aspect.ExceptionPath.forNotValidExceptionSaveStaff()", throwing = "ex")
    public void handleNotValidException(JoinPoint theJoinPoint, MethodArgumentNotValidException ex) {
        String method = theJoinPoint.getSignature().toShortString();
        logger.error("Exception in method {}: {}", method, ex.getMessage());
    }
/*

    @AfterThrowing(pointcut = "com.myproject.Collection.aspect.ExceptionPath.forJWTResolveToken()", throwing = "ex")
    public void handleJWTResolveTokenException(JoinPoint theJoinPoint, Throwable ex) {
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("TT");

        if (ex instanceof JWTDecodeException) {
            JWTServiceImplementLogger.error("JWTDecodeException in method {}: {}", method, ex.getMessage());
        } else if (ex instanceof SignatureVerificationException) {
            JWTServiceImplementLogger.error("SignatureVerificationException in method {}: {}", method, ex.getMessage());
        } else if (ex instanceof JWTVerificationException) {
            JWTServiceImplementLogger.error("JWTVerificationException in method {}: {}", method, ex.getMessage());
        } else {
            JWTServiceImplementLogger.error("Exception in method {}: {}", method, ex.getMessage());
        }
    }*/


}
