package com.licence.dto;

import com.licence.models.Customer;

public class CustomerDto {
    String surname;
    String firstname;
    String contact;
    String email;
    String address;

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "CustomerDto [surname=" + surname + ", firstname=" + firstname + ", contact=" + contact + ", email="
                + email + ", address=" + address + "]";
    }

    public Customer getCustomer(){
        return new Customer(surname,firstname,email,contact,address);
    }

    
    

}
