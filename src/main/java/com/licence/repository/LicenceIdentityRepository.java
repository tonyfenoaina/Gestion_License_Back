package com.licence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import com.licence.models.LicenceIdentity;

import jakarta.transaction.Transactional;

public interface LicenceIdentityRepository extends JpaRepository<LicenceIdentity,Long>{
    
    @Query("SELECT l FROM LicenceIdentity l WHERE l.licence.id = :idLicence")
    @Transactional
    List<LicenceIdentity> findByLicence_id(@Param("idLicence") Long idLicence);

    @Query("SELECT l FROM LicenceIdentity l WHERE l.idLicence = :idLicence AND l.idPc = :idPc")
    @Transactional
    LicenceIdentity findByIdLicenceAndIdPc(@Param("idLicence") String idLicence,@Param("idPc")String idPc);

}
