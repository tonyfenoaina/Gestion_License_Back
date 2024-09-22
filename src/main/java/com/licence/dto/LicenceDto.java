package com.licence.dto;

import java.util.Date;

import com.licence.models.Customer;
import com.licence.models.Licence;
import com.licence.models.Software;

public class LicenceDto {

    Long idCustomer;
    Long idSoftware;

    Date dateStart;

    Date dateEnd;

    Integer numberActivation;

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getIdSoftware() {
        return idSoftware;
    }

    public void setIdSoftware(Long idSoftware) {
        this.idSoftware = idSoftware;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getNumberActivation() {
        return numberActivation;
    }

    public void setNumberActivation(Integer numberActivation) {
        this.numberActivation = numberActivation;
    }

    public Licence setLicence(Customer customer,Software software){
        System.out.println(customer);
        Licence licence = new Licence();
        licence.setUsers(customer);
        licence.setSoftware(software);
        licence.setStartDate(dateStart);
        licence.setEndDate(dateEnd);
        licence.setNumberActivation(numberActivation);
        return licence;
    }
    
}
