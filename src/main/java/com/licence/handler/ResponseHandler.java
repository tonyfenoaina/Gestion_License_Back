package com.licence.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseHandler {

    public static String showError(Exception exception, HttpStatus httpStatus) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Error", exception.getMessage());
        map.put("Status", httpStatus.value());
        return mapper.writeValueAsString(map);
    }


}
