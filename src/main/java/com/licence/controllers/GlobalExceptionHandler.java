package com.licence.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        // Log l'exception et retourne une réponse d'erreur
        return new ResponseEntity<>("Une erreur s'est produite : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
