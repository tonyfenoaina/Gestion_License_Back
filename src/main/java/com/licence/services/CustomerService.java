package com.licence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.licence.models.Customer;
import com.licence.models.User;
import com.licence.repository.CustomerRepository;
import com.licence.repository.UserRepository;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    @Lazy
    UserService userService;

    

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public List<Customer> getByUser(String token){
        User user = userService.getUserByToken(token);
        return customerRepository.findByUser(user);
    }
    
}
