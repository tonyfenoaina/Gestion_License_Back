package com.licence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Licence;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LicenceRepository extends JpaRepository<Licence,Long>{
    
    @Query("SELECT l FROM Licence l WHERE l.users.id = :idCustomer AND l.state = 1")
    Page<Licence> findByUserId(@Param("idCustomer") Long idCutosmer,Pageable pageable);

    @Query("UPDATE licence l SET l.state = 0 WHERE l.id = :id")
    void updateState(@Param("id") Long id);

}
