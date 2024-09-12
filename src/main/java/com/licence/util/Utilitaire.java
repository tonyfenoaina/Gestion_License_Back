package com.licence.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

public class Utilitaire {
    
    public static String convertMultipartFileToBase64(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);
        return base64Encoded;
    }

}
