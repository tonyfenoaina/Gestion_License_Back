package com.licence.response;

public class Error {
    
    final int error = 1;
    private String message;

    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Error(String message) {
        this.message = message;
    }

    public static Error init(String message){
        return new Error(message);
    }

    
    
    

}
