package com.licence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.licence.models.Customer;
import com.licence.models.Role;
import com.licence.models.User;

import jakarta.transaction.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByRole_CodeRole(String codeRole);

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.role.codeRole = :codeRole AND u.state=1")
    Page<User> findByRole_CodeRole(String codeRole, Pageable pageable);

    Page<User> findByEmailLike(@Param("email") String email, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.state = 0 WHERE u.id = :userId")
    void updateState(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.surname ILIKE %:name%")
    List<User> searchBySurname(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.firstname ILIKE %:name%")
    List<User> searchByFirstname(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.email ILIKE %:email%")
    List<User> searchByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE (u.surname ILIKE %:keyword% OR u.firstname ILIKE %:keyword% OR u.email ILIKE %:keyword%) AND u.state = 1")
    Page<User> advancedSearch(@Param("keyword") String keyword, Pageable pageable);
}
