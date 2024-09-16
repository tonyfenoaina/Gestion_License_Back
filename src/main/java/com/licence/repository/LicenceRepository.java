package com.licence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Licence;

public interface LicenceRepository extends JpaRepository<Licence,Long>{
    
    

}
