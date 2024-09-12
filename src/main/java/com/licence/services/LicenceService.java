package com.licence.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Date;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.LicenseReader;
import javax0.license3j.io.LicenseWriter;;

@Service
public class LicenceService {

    File licence_directory; 
    File licence_file; 

    String algorithme = "RSA";

    int size = 2048;
    License license;

    @Value("${licence-format}")
    private String format;

    @Value("${licence-digest}")
    private String digest;

    LicenseKeyPair licenseKeyPair;


    public void generateKeys(String algorithme,int size){
        try {
            licenseKeyPair = LicenseKeyPair.Create.from(algorithme, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create_licence(Date expirationDate) throws IOException{
        license = new License();
        license.setLicenseId();
        generateKeys(algorithme, size);
        license.setExpiry(expirationDate);
    }


    public void getLicence(){
        try {
            license = new LicenseReader(licence_file).read();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void save_licence() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException{
        if (!(licence_directory.isDirectory() && licence_directory.exists())) {
            licence_directory.mkdirs();
        }
        if (!(licence_file.exists())) {
            licence_file.createNewFile();
        }
        license.sign(licenseKeyPair.getPair().getPrivate(), digest);
        LicenseWriter writer = new LicenseWriter(licence_file);
        writer.write(license,IOFormat.valueOf(format));
        writer.close();
    }

    public boolean licence_verification(PublicKey publicKey){
        if (!license.isExpired()) {
            return license.isOK(publicKey);
        }
        return false;
    }

    public  byte[] convertPropertyToBytes(Properties properties) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(properties);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public  Properties convertBytesToProperty(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Properties) objectInputStream.readObject();
    }

    public Feature createFeature(String name,Properties properties) throws IOException{
        return Feature.Create.binaryFeature(name, convertPropertyToBytes(properties));
    }


    public void addToLicence(Feature feature){
        license.add(feature);

    }

    public void init(String idPc){
        licence_file = new File("license/"+idPc+"/license.bin");
        licence_directory = new File("license/"+idPc);
    }

}
