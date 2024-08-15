package com.licence.services;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.License.Create;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.KeyPairWriter;;

@Service
public class LicenceService {
    
    String public_key="ICP";
    String private_key="lskqdjqslksdjqkl";

    String format = "BINARY";
    int size = 2048;
    String digest = "SHA-256";

    String algorithme = "RSA";

    License license;
    LicenseKeyPair licenseKeyPair;


    public void generateKeys(String algorithme,int size){
        try {
            licenseKeyPair = LicenseKeyPair.Create.from(algorithme, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public LicenceService(){
        license = new License();
        generateKeys(algorithme, size);
        var ioFormat = IOFormat.valueOf(format);
        try {
            final var writer = new KeyPairWriter(FileService.getFile("PRIVATE_KEY"), FileService.getFile("PUBLIC_KEY"));
            writer.write(licenseKeyPair,ioFormat);
        } catch (Exception e) {
            e.printStackTrace();
        };
        try {
            license.sign(licenseKeyPair.getPair().getPrivate(), digest);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException
                | IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            digestPublicKey();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public void digestPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException{
        var key = licenseKeyPair.getPair().getPublic().getEncoded();

        
        var code = new StringBuilder();
        for (int i = 0; i < key.length; i++) {
            int intVal =  key[i] & 0xff;
            code.append(String.format("%02X",intVal));
            if ((i + 1) % 4 == 0 && (i + 1) != key.length) {
                code.append("-");
            }
        }

        System.out.println(code.toString());
        
        getPublicKey(code.toString());


        // System.out.println("Licence ok :"+license.isOK(publicKey));
    }

    public PublicKey getPublicKey(String code) throws InvalidKeySpecException, NoSuchAlgorithmException{
        
        String licenseKeyClean = code.replace("-", "");
        byte[] keyBytes = new byte[licenseKeyClean.length() / 2];
        for (int i = 0; i < licenseKeyClean.length(); i += 2) {
            keyBytes[i / 2] = (byte) ((Character.digit(licenseKeyClean.charAt(i),16)<<4) + Character.digit(licenseKeyClean.charAt(i+1), 16));
        }

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;

    }


    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getAlgorithme() {
        return algorithme;
    }

    public void setAlgorithme(String algorithme) {
        this.algorithme = algorithme;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public LicenseKeyPair getLicenseKeyPair() {
        return licenseKeyPair;
    }

    public void setLicenseKeyPair(LicenseKeyPair licenseKeyPair) {
        this.licenseKeyPair = licenseKeyPair;
    }

}
