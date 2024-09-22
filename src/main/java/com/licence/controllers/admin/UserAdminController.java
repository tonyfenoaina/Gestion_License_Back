package com.licence.controllers.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.licence.dto.UserDto;
import com.licence.response.Success;
import com.licence.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/secure/admin/users")
public class UserAdminController {
    @Autowired
    private UserService userService;


    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }
   
    @GetMapping("/getUser")
    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return userService.getUserByRole("USER",page, size);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Long id){
        userService.updateStateUser(id);
        return new ResponseEntity<>(Success.init("Delete success"),HttpStatus.OK);
    }

}
