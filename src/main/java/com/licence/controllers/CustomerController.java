package com.licence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.licence.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    
    @Autowired
    CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(customerService.getAll(),HttpStatus.OK);
    }
    
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(customerService.getByUser(token),HttpStatus.OK);
    }

}
