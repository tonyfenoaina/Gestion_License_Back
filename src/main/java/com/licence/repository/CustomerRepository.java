package com.licence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.licence.models.Customer;
import com.licence.models.Software;
import com.licence.models.User;

import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,Long>{
    List<Customer> findByUser(User user);
    Page<Customer> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.state = 0 WHERE c.id = :id")
    void updateState(Long id);

    @Query("SELECT c FROM Customer c WHERE c.surname ILIKE %:name%")
    List<Customer> searchBySurname(@Param("name")String name);

    @Query("SELECT c FROM Customer c WHERE c.firstname ILIKE %:name%")
    List<Customer> searchByFirstname(@Param("name")String name);
    
    @Query("SELECT c FROM Customer c WHERE c.email ILIKE %:email%")
    List<Customer> searchByEmail(@Param("email")String email);

    @Query("SELECT u FROM Customer u WHERE (u.surname ILIKE %:keyword% or u.firstname ILIKE %:keyword% or u.email ILIKE %:keyword%) and u.state = 1" )
    Page<Customer> advancedSearch(@Param("keyword") String keyword, Pageable pageable);

}
