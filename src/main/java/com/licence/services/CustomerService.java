package com.licence.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.licence.dto.CustomerDto;
import com.licence.models.Customer;
import com.licence.models.User;
import com.licence.repository.CustomerRepository;
import java.util.Optional;


@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    @Lazy
    UserService userService;

    public ResponseEntity<?> getAll(int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return new ResponseEntity<>(customerPage,HttpStatus.OK);
    }

    public List<Customer> getByUser(String token){
        User user = userService.getUserByToken(token);
        return customerRepository.findByUser(user);
    }

    public ResponseEntity<?> add(CustomerDto customerDto,String authorizationToken){
        User user = userService.getUserByToken(authorizationToken);
        Customer customer = customerDto.getCustomer();
        customer.setUser(user);
        customerRepository.save(customer);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long idCustomer,CustomerDto customerDto,String authorizationToken){
        User user = userService.getUserByToken(authorizationToken);
        Customer customer = customerDto.getCustomer();
        customer.setId(idCustomer);
        customer.setUser(user);
        customerRepository.save(customer);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    public void delete(Long idCustomer){
        customerRepository.updateState(idCustomer);
    }

    public Customer getById(Long idCustomer){
        Optional<Customer> customer = customerRepository.findById(idCustomer);
        return customer.orElse(null);
    }
    
}
