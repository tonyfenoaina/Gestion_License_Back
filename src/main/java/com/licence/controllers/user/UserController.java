package com.licence.controllers.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.licence.dto.UserDto;
import com.licence.models.User;
import com.licence.services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/secure/user")
public class UserController {

    @Autowired 
    @Lazy
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<?> getData(@RequestHeader("Authorization")String token){
        return new ResponseEntity<>(userService.getUserByToken(token),HttpStatus.OK);
    }

    @PutMapping("/update/data")
    public ResponseEntity<?> updateData(@RequestHeader("Authorization")String token,@RequestBody UserDto userDto) {
        User user = userService.updateUserData(userDto, token);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    
    @PutMapping("/update/photo")
    public ResponseEntity<?> updatePhoto(@RequestHeader("Authorization")String token,@RequestParam MultipartFile photo) throws IOException {
        User user = userService.updateUserPhoto(photo, token);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
