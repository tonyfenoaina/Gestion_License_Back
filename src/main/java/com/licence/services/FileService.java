package com.licence.services;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Classe pour trouver un fichier dans le projet
 * @author Mamitiana
 */
public class FileService {
    
    private static String search(File directory,String nameFile){
        File[] files=directory.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                if(!file.getName().contains(".")){
                    if(FileService.search(file,nameFile)!=null){
                        return FileService.search(file,nameFile);
                    }
                }
            }
            else{
                if(file.getName().equalsIgnoreCase(nameFile)){
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }
    

    /**
     *Fonction permetant de recuperer le chemin du fichier
     * @param name le nom du fichier
     * @return le chemin du fichier
     */
    public static File getFile(String name){
        String filePath=FileService.search(new File("."), name);
        if(filePath!=null){
            return new File(filePath.replaceAll("\\.\\\\",""));
        }
        return null;
    }
    

    public String convertToBase64(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(fileBytes);
    }
    
    
}
