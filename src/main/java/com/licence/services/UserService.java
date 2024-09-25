package com.licence.services;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.licence.dto.LoginDto;
import com.licence.dto.UserDto;
import com.licence.handler.ResponseHandler;
import com.licence.models.Role;
import com.licence.models.User;
import com.licence.repository.RoleRepository;
import com.licence.repository.UserRepository;
import com.licence.response.LoginResponse;
import com.licence.util.Utilitaire;


@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;

    @Autowired
    @Lazy
    TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getRole().getCodeRole()))
        );
    }

    public User getUserByToken(String token){
        String authorizationToken = token.replaceAll("Bearer ", "");
        String email = tokenService.extractEmail(authorizationToken);
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<?> login(LoginDto loginRequest) throws IOException {
        String email = loginRequest.getEmail();
        String password = passwordEncoder.encode(loginRequest.getPassword());
        User user = userRepository.findByEmail(email);
        if (user==null) {
            return new ResponseEntity<>(ResponseHandler.showError(new Exception("User not found"),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);   
        }
        if (user.getPassword().equals(password)) {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
            );
            return new ResponseEntity<>(new LoginResponse(tokenService.createToken(email),user),HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseHandler.showError(new Exception("Wrong password"),HttpStatus.UNAUTHORIZED),HttpStatus.UNAUTHORIZED);
    }


    
    public List<User> getUsersByRole(String codeRole){
        return userRepository.findByRole_CodeRole(codeRole);
    }

    public ResponseEntity<?> getUserByRole(String codeRole,int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findByRole_CodeRole(codeRole,pageable);
        return new ResponseEntity<>(userPage,HttpStatus.OK);
    }

    public ResponseEntity<?> getUsers(int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        return new ResponseEntity<>(userPage,HttpStatus.OK); 
    }


    public List<User> getAll(){
        return userRepository.findAll();
    }

    public ResponseEntity<?> createUser(UserDto userDto){
        System.out.println(userDto);
        User user = userDto.getUser();
        Role role = roleRepository.findByCodeRole("USER");
        user.setRole(role);
        userRepository.save(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    

    public void updateStateUser(Long id){
        userRepository.updateState(id);
    }

    public User updateUserData(UserDto userDto,String token){
        User user = getUserByToken(token);
        User newUser = userDto.getUser();
        newUser.setId(user.getId());
        newUser.setContact(user.getPhoto());
        newUser.setRole(user.getRole());
        return userRepository.save(newUser);
    }
    
    public User updateUserPhoto(MultipartFile photo,String token) throws IOException{
        User user = getUserByToken(token);
        String photo64 = Utilitaire.convertMultipartFileToBase64(photo);
        user.setPhoto(photo64);
        return userRepository.save(user);
    }

}
