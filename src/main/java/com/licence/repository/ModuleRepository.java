package com.licence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Module;
import com.licence.models.Software;
import com.licence.models.User;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModuleRepository extends JpaRepository<Module,Long>{
    
    @Query("SELECT m FROM Module m WHERE m.software.id = :software AND m.state=1")
    Page<Module> findAll(@Param("software") Long software,Pageable pageable);

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

    @Query("SELECT m FROM Module m WHERE m.name ILIKE %:name% AND m.software.id = :software")
    List<Module> searchByName(@Param("name")String name,@Param("software") Long software);

    @Query("SELECT u FROM Module u WHERE u.name ILIKE %:keyword% and u.software.id= :software and u.state=1")
    Page<Module> advancedSearch(@Param("keyword") String keyword,String software, Pageable pageable);

}
