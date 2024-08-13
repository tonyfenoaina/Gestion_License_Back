package com.licence.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Role;
import com.licence.models.User;
import java.util.List;



public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);  
    List<User> findByRole(Role role); 
    List<User> findByRole_CodeRole(String codeRole); 


    Page<User> findAll(Pageable pageable);
    Page<User> findBySurnameContainingIgnoreCase(String surname,Pageable pageable);
    Page<User> findByFirstnameContainingIgnoreCase(String firstname,Pageable pageable);
    Page<User> findByEmailContainingIgnoreCase(String email,Pageable pageable);
}


