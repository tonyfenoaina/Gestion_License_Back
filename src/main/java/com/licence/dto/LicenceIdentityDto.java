package com.licence.dto;
import java.util.List;
public class LicenceIdentityDto {
    
    Long idLicence;

    List<String> listIdPc;

    public Long getIdLicence() {
        return idLicence;
    }

    public void setIdLicence(Long idLicence) {
        this.idLicence = idLicence;
    }

    public List<String> getListIdPc() {
        return listIdPc;
    }

    public void setListIdPc(List<String> listIdPc) {
        this.listIdPc = listIdPc;
    }

    

}
