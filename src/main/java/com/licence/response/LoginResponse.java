package com.licence.response;

import com.licence.models.User;

public class LoginResponse {

    private User user;
    private String token;

    public String getToken() {
        return token;
    }

    
    
    public LoginResponse(String token,User user) {
        this.user = user;
        this.token = token;
    }



    public User getUser() {
        return user;
    }



    public void setUser(User user) {
        this.user = user;
    }



    public void setToken(String token) {
        this.token = token;
    }

    
}
