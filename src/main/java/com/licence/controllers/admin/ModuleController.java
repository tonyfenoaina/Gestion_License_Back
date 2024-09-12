package com.licence.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.licence.response.Success;
import com.licence.services.ModuleService;
import com.licence.models.Module;


@RestController
@RequestMapping("/secure/admin/module")
public class ModuleController {

    @Autowired
    @Lazy
    private ModuleService moduleService;

    @GetMapping("/getAll/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id")Long idSoftware,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return moduleService.getAll(idSoftware,page,size);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> save(String name,long software){
        return moduleService.save(name, software);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable("id")Long id){
        return new ResponseEntity<>(Success.init("Delete success"),HttpStatus.OK);
    }

    @PutMapping("/update/{id}/name")
    public ResponseEntity<?> updateName(@PathVariable("id") Long id,@RequestParam String name) {
        Module module = moduleService.getByID(id);
        if(module==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
        moduleService.updateName(id, name);
        return new ResponseEntity<>(module,HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}/photo")
    public ResponseEntity<?> updatePhoto(@PathVariable("id") Long id,@RequestParam MultipartFile photo)throws IOException {
        Module module = moduleService.getByID(id);
        if(module==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        moduleService.updatePhoto(photo, id);
        return new ResponseEntity<>(module,HttpStatus.OK);
    }
}
