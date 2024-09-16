package com.licence.dto;

import com.licence.models.Licence;
import com.licence.models.LicenceIdentity;
import com.licence.models.Module;

import java.util.List;

public class LicenceData {
    
    Licence licence;

    List<Module> modules;

    List<LicenceIdentity> licenceIdentities;

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<LicenceIdentity> getLicenceIdentities() {
        return licenceIdentities;
    }

    public void setLicenceIdentities(List<LicenceIdentity> licenceIdentities) {
        this.licenceIdentities = licenceIdentities;
    }

    

}
