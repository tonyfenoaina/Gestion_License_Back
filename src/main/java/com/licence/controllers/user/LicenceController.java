package com.licence.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    @PostMapping("/addPc")
    public ResponseEntity<?> addPc(@RequestBody LicenceIdentityDto licenceIdentityDto) throws Exception {
        return new ResponseEntity<>(licenceService.addPc(licenceIdentityDto),HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> postMethodName() {
        return new ResponseEntity<>(licenceService.getAll(),HttpStatus.OK);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLicenceData(@PathVariable("id") Long idLicence) {
        return new ResponseEntity<>(licenceService.getDataLicence(idLicence),HttpStatus.OK);
    }

    @GetMapping("/getLicence")
    public ResponseEntity<?> getLicence(@RequestParam String idPc,@RequestParam String idLicence) {
        return licenceService.isLicenceOK(idPc, idLicence);
    }
    
    
    

}