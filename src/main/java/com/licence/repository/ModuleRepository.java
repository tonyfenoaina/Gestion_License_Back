package com.licence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Module;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ModuleRepository extends JpaRepository<Module,Long>{
    
    @Query("SELECT m FROM Module m WHERE  m.state=1")
    Page<Module> findAll(Long software,Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Module m SET m.state = 0 WHERE m.id = :id")
    void updateState(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Module m SET m.photo = :photo WHERE m.id = :id")
    void updatePhoto(String photo,Long id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Module m SET m.name = :name WHERE m.id = :id")
    void updateName(String name,Long id);

}
