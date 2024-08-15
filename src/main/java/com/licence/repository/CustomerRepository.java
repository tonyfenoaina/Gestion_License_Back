package com.licence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Customer;
import com.licence.models.User;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    List<Customer> findByUser(User user);

    
}
