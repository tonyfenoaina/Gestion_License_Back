package com.licence.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.licence.dto.CustomerDto;
import com.licence.services.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.licence.response.Success;


@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    
    @Autowired
    CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return customerService.getAll(page, size);
    }
    
    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestHeader("Authorization") String authorizationToken){
        return new ResponseEntity<>(customerService.getByUser(authorizationToken),HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CustomerDto customerDto,@RequestHeader("Authorization") String authorizationToken){
        return customerService.add(customerDto,authorizationToken);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Long idCustomer,@RequestBody CustomerDto customerDto,@RequestHeader("Authorization") String authorizationToken){
        return customerService.update(idCustomer,customerDto,authorizationToken);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long idCustomer){
        customerService.delete(idCustomer);
        return new ResponseEntity<>(Success.init("Delete success"),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("search")String search){
        return customerService.search(search);
    }

    @GetMapping("/searchh")
    public ResponseEntity<?> searchUsers(@RequestParam("keyword") String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        System.out.println("key: "+keyword);
        System.out.println(customerService.advancedSearch(keyword,page,size));
        return customerService.advancedSearch(keyword,page,size);
    }

}
