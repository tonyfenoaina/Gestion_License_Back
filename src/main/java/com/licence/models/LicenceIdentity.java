package com.licence.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "licence_identity")
public class LicenceIdentity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "licence")
    @JsonManagedReference
    Licence licence;

    @Column(name = "id_pc")
    String idPc;

    @Column(name = "id_licence")
    String idLicence;


    @Lob
    @Column(name = "public_key",columnDefinition = "text")
    String publicKey;

    @Column(name = "mode_activation")
    Integer modeActivation;

    @Column(name = "state")
    Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public String getIdPc() {
        return idPc;
    }

    public void setIdPc(String idPc) {
        this.idPc = idPc;
    }

    public String getIdLicence() {
        return idLicence;
    }

    public void setIdLicence(String idLicence) {
        this.idLicence = idLicence;
    }

    public Integer getModeActivation() {
        return modeActivation;
    }

    public void setModeActivation(Integer modeActivation) {
        this.modeActivation = modeActivation;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "LicenceIdentity [id=" + id + ", licence=" + licence + ", idPc=" + idPc + ", idLicence=" + idLicence
                + ", publicKey=" + publicKey + ", modeActivation=" + modeActivation + ", state=" + state + "]";
    }


    

    
    
}
