package com.licence.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "licence")
public class Licence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="users")
    @JsonManagedReference
    private Customer users;

    @ManyToOne
    @JoinColumn(name = "software")
    @JsonManagedReference
    private Software software;

    @Column(name = "startdate")
    private Date startDate; 

    @Column(name = "enddate")
    private Date endDate;

    @Column
    private Integer numberActivation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getUsers() {
        return users;
    }

    public void setUsers(Customer users) {
        this.users = users;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getNumberActivation() {
        return numberActivation;
    }

    public void setNumberActivation(Integer numberActivation) {
        this.numberActivation = numberActivation;
    }

    

}
