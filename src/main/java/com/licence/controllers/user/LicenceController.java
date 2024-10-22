package com.licence.controllers.user;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.licence.dto.LicenceDto;
import com.licence.dto.LicenceIdentityDto;
import com.licence.dto.LicenceModuleDto;
import com.licence.response.Success;
import com.licence.services.LicenceService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/public/user/licence")
public class LicenceController {
    
    @Autowired
    LicenceService licenceService;

    @PostMapping("/addLicence")
    public ResponseEntity<?> addLicence(@RequestBody LicenceDto licenceDto) {
        System.out.println("licence::");
        System.out.println(licenceDto.getIdCustomer()+"-"+licenceDto.getIdSoftware()+"-"+licenceDto.getNumberActivation()+"-"+licenceDto.getDateEnd()+"-"+licenceDto.getDateStart());
        return new ResponseEntity<>(licenceService.addLicence(licenceDto),HttpStatus.OK);
    }

    @PostMapping("/addModule")
    public ResponseEntity<?> addModule(@RequestBody LicenceModuleDto licenceModuleDto) {
        return new ResponseEntity<>(licenceService.addModule(licenceModuleDto),HttpStatus.OK);
    }

    @GetMapping("/getIdPc")
    public ResponseEntity<?> getIdPc(@RequestParam("idLicence") Long idLicence) {
        return licenceService.sendListIdPc(idLicence);
    }
    
    
    @PostMapping("/addPc")
    public ResponseEntity<?> addPc(@RequestBody LicenceIdentityDto licenceIdentityDto) throws Exception {
        return new ResponseEntity<>(licenceService.addPc(licenceIdentityDto),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> postMethodName() {
        return new ResponseEntity<>(licenceService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/getAllList")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return licenceService.getAllList(page, size);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLicenceData(@PathVariable("id") Long idLicence) {
        return new ResponseEntity<>(licenceService.getDataLicence(idLicence),HttpStatus.OK);
    }

    @GetMapping("/getLicenceIdentityActive")
    public ResponseEntity<?> getLicenceIdentityActive(@RequestParam("idLicence")Long idLicence){
        return licenceService.getLicenceIdentityActive(idLicence);
    }

    @GetMapping("/getLicenceIdentityNonActive")
    public ResponseEntity<?> getLicenceIdentityNonActive(@RequestParam("idLicence")Long idLicence){
        return licenceService.getLicenceIdentityNonActive(idLicence);
    }

    @GetMapping("/activeLicence_manuel")
    public ResponseEntity<?> activeLicenceManuel(@RequestParam String idPc,@RequestParam String idLicence) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, IOException {
        return licenceService.activeLicence(idPc, idLicence,1);
    }

    @GetMapping("/activeLicence_auto")
    public ResponseEntity<?> activeLicenceAuto(@RequestParam String idPc,@RequestParam String idLicence) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, IOException {
        return licenceService.activeLicence(idPc, idLicence,2);
    }
    

    @GetMapping("/verifyLicence")
    public ResponseEntity<?> verifieLicence(@RequestParam("tokenLicence")String token) throws JsonMappingException, JsonProcessingException{
        return licenceService.getLicenceByToken(token);
    }
    

    @GetMapping("/getAllLicenceByCustomer")
    public ResponseEntity<?> getAllLicenceByCustomer(@RequestParam("idCustomer")Long idCustomer, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return licenceService.getAllLicenceByCustomer(idCustomer, page, size);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadPdf(@RequestParam("idLicence")Long idLicence){
        return licenceService.getDataLicencePdf(idLicence);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteLicence(@PathVariable("id") Long idLicence) {
        return licenceService.updateState(idLicence);
    }
    

}
