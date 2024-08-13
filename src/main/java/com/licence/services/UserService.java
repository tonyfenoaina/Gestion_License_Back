package com.licence.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.licence.handler.ResponseHandler;
import com.licence.models.User;
import com.licence.repository.UserRepository;
import com.licence.request.LoginRequest;


@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority(user.getRole().getCodeRole()))
        );
    }


    public User getUserByToken(String token){
        String email = tokenService.extractEmail(token);
        String role = tokenService.extractRole(token);
        User user = userRepository.findByEmail(email);
        if (user.getRole().getCodeRole().equals(role)) {
            return user;
        }
        return null;
    }

    public ResponseEntity<?> getUser(String t){
        String token = t.replaceAll("Bearer ", "");
        User user = getUserByToken(token);
        if (tokenService.isTokenExpired(token)) {
            return new ResponseEntity<>(ResponseHandler.showResponse("Token expired"),HttpStatus.UNAUTHORIZED);
        }
        if (user!=null) return new ResponseEntity<>(user,HttpStatus.OK);
        return new ResponseEntity<>(ResponseHandler.showResponse("Token not found"),HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user==null) {
            return new ResponseEntity<>(ResponseHandler.showResponse("Email not found"),HttpStatus.NOT_FOUND);
        }
        if (user.getPassword().equals(loginRequest.getPassword())) {
            return new ResponseEntity<>(tokenService.createToken(loginRequest.getEmail(), user.getRole().getCodeRole()),HttpStatus.NOT_FOUND);
        }
        // if (passwordEncoder.matches(password, user.getPassword())) {
        //     return new ResponseEntity<>(tokenService.createToken(email, user.getRole().getCodeRole()),HttpStatus.NOT_FOUND);
        // }
        return new ResponseEntity<>(ResponseHandler.showResponse("Invalid credentials"),HttpStatus.UNAUTHORIZED);
    }
    
    public List<User> getUsersByRole(String codeRole){
        return userRepository.findByRole_CodeRole(codeRole);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


}
