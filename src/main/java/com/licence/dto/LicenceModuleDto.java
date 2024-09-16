package com.licence.dto;

import java.util.List;

public class LicenceModuleDto {
    
    Long idLicence;
    List<Long> listIdModule;
    public Long getIdLicence() {
        return idLicence;
    }
    public void setIdLicence(Long idLicence) {
        this.idLicence = idLicence;
    }
    public List<Long> getListIdModule() {
        return listIdModule;
    }
    public void setListIdModule(List<Long> listIdModule) {
        this.listIdModule = listIdModule;
    }

    

}
