package com.licence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.licence.models.Customer;
import com.licence.models.User;

import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    List<Customer> findByUser(User user);
    Page<Customer> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.state = 0 WHERE c.id = :id")
    void updateState(Long id);

}
