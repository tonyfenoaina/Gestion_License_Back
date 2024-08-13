package com.licence.handler;


public class ResponseHandler {


    public static Response showResponse(String message){
        return new Response(message);
    }

    private static class Response {
        
        private final String message;

        public Response(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        
        
    }

}
