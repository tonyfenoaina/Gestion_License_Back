package com.licence.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.licence.services.ModuleService;


@RestController
@RequestMapping("/secure/user/module")
public class ModuleUserController {

    @Autowired
    @Lazy
    private ModuleService moduleService;

    @GetMapping("/getAll/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id")Long idSoftware,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return moduleService.getAll(idSoftware,page,size);
    }
}
