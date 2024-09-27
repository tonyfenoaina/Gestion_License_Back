package com.licence.repository;

import com.licence.models.Software;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;




public interface SoftwareRepository  extends JpaRepository<Software,Long>{
    
    @Query("SELECT s FROM Software s WHERE  s.state=1")
    Page<Software> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Software s SET s.state = 0 WHERE s.id = :id")
    void updateState(Long id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Software s SET s.photo = :photo WHERE s.id = :id")
    void updatePhoto(String photo,Long id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Software s SET s.name = :name WHERE s.id = :id")
    void updateName(String name,Long id);

    @Query("SELECT s FROM Software s WHERE s.name LIKE %:name%")
    List<Software> searchByName(@Param("name")String name);
}
