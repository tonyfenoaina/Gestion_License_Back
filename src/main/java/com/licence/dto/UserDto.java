package com.licence.dto;

import com.licence.models.User;

public class UserDto {
    
    private String email;
    private String surname;
    private String firstname;
    private String password;
    private String contact;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public UserDto() {
    }
    public UserDto(String email, String surname, String firstname, String password, String contact) {
        this.email = email;
        this.surname = surname;
        this.firstname = firstname;
        this.password = password;
        this.contact = contact;
    }
    @Override
    public String toString() {
        return "UserDto [email=" + email + ", surname=" + surname + ", firstname=" + firstname + ", password="
                + password + ", contact=" + contact + "]";
    }
    

    
    public User getUser(){
        User user = new User(surname, firstname, email, contact, password);
        return user;
    }

    
    

    
    
}
