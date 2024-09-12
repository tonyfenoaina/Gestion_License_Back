package com.licence.response;

public class Success {
    
    final int success = 1;
    private String message;

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Success(String message) {
        this.message = message;
    }

    public static Success init(String message){
        return new Success(message);
    }
    
    
    

}
