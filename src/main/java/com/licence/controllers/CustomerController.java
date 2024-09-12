package com.licence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.licence.dto.CustomerDto;
import com.licence.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> get(@RequestHeader("Authorization") String authorizationToken){
        return new ResponseEntity<>(customerService.getByUser(authorizationToken),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CustomerDto customerDto,@RequestHeader("Authorization") String authorizationToken){
        return customerService.add(customerDto,authorizationToken);
    }

}
