package com.licence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.licence.models.LicenceModule;
import java.util.List;

public interface LicenceModuleRepository extends JpaRepository<LicenceModule,Long>{
    
    @Query("SELECT l FROM LicenceModule l WHERE l.licence.id = :idLicence")
    List<LicenceModule> findByLicenceId(@Param("idLicence") Long idLicence);

}
