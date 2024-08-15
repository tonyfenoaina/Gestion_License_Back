package com.licence.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.licence.models.User;
import com.licence.services.UserService;

@RestController
@RequestMapping("/secure/admin/users")
public class UserAdminController {
    @Autowired
    private UserService userService;

    
    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/getUser")
    public List<User> getAllUser() {
        return userService.getUsersByRole("USER");
    }
    
    @GetMapping
    public ResponseEntity<?> getUsers(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "5") int size) {
        return userService.getUsers(page, size);
    }
}
