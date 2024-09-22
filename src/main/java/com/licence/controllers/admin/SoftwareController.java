package com.licence.controllers.admin;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.licence.models.Software;
import com.licence.response.Success;
import com.licence.services.SoftwareService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/secure/admin/software")
@CrossOrigin
public class SoftwareController {

    @Autowired
    @Lazy
    SoftwareService softwareService;

    @PostMapping("/add")
    public ResponseEntity<?> postMethodName(@RequestParam("photo")MultipartFile photo, @RequestParam String name) throws IOException {
        System.out.println("eto");
        return softwareService.save(photo,name);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        System.out.println("tes");
        return softwareService.getAll(page, size);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        softwareService.updateState(id);
        return new ResponseEntity<>(Success.init("Delete success"),HttpStatus.OK);
    }

    @PutMapping("/update/{id}/name")
    public ResponseEntity<?> updateName(@PathVariable("id") Long id,@RequestParam String name) {
        Software software = softwareService.getByID(id);
        if(software==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
        softwareService.updateName(id, name);
        return new ResponseEntity<>(software,HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}/photo")
    public ResponseEntity<?> updatePhoto(@PathVariable("id") Long id,@RequestParam MultipartFile photo)throws IOException {
        Software software = softwareService.getByID(id);
        if(software==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        softwareService.updatePhoto(photo, id);
        return new ResponseEntity<>(software,HttpStatus.OK);
    }

}
