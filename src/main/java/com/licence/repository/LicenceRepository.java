package com.licence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.licence.models.Licence;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LicenceRepository extends JpaRepository<Licence,Long>{
    
    @Query("SELECT l FROM Licence l WHERE l.users.id = :idCustomer")
    List<Licence> findByUserId(@Param("idCustomer") Long idCutosmer);

}
