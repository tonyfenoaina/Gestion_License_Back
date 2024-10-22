package com.licence.controllers.admin;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.licence.dto.UserDto;
import com.licence.models.User;
import com.licence.response.Success;
import com.licence.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

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

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam("keyword") String keyword, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        System.out.println("key: "+keyword);
        System.out.println(userService.advancedSearch(keyword,page,size));
        return userService.advancedSearch(keyword,page,size);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Long id){
        userService.updateStateUser(id);
        return new ResponseEntity<>(Success.init("Delete success"),HttpStatus.OK);
    }

    @PutMapping(value = "/update/data")
    public ResponseEntity<?> updateData(@RequestHeader("Authorization") String token,
    @RequestParam("id") Long id,
    @RequestParam("firstname") String firstname,
    @RequestParam("email") String email,
    @RequestParam("contact") String contact,
    @RequestParam("surname") String surname,
    @RequestParam("password") String password) {
        UserDto userDto = new UserDto(email, surname, firstname, password, contact);
        User user = userService.updateUserData(userDto, token);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    
    @PutMapping("/update/photo")
    public ResponseEntity<?> updatePhoto(@RequestHeader("Authorization")String token,@RequestParam MultipartFile photo) throws IOException {
        System.out.println("ETO OOOOO");
        User user = userService.updateUserPhoto(photo, token);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

}
