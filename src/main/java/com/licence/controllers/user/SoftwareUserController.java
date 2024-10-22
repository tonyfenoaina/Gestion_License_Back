package com.licence.controllers.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.licence.services.SoftwareService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/secure/user/software")
@CrossOrigin
public class SoftwareUserController {

    @Autowired
    @Lazy
    SoftwareService softwareService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        System.out.println("tes");
        return softwareService.getAll(page, size);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam("keyword") String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        System.out.println("key: "+keyword);
        System.out.println(softwareService.advancedSearch(keyword,page,size));
        return softwareService.advancedSearch(keyword,page,size);
    }
}
