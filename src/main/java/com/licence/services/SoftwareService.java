package com.licence.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.licence.models.Software;
import com.licence.repository.SoftwareRepository;
import com.licence.util.Utilitaire;

@Service
public class SoftwareService {
    
    @Autowired
    @Lazy
    private SoftwareRepository softwareRepository;

    public ResponseEntity<?> save(MultipartFile photo,String name) throws IOException{
        Software software = new Software();
        software.setName(name);
        software.setPhoto(Utilitaire.convertMultipartFileToBase64(photo));
        software.setDateCreation(new Date());
        software.setState(1);
        softwareRepository.save(software);
        return new ResponseEntity<>(software,HttpStatus.OK);
    }

    public Software getByID(Long id){
        Optional<Software> software = softwareRepository.findById(id);
        return software.orElse(null);
    }


    public ResponseEntity<?> getAll(int page,int size){
        System.out.println("tonga eto soft");
        Pageable pageable= PageRequest.of(page, size);
        Page<Software> softwarePage = softwareRepository.findAll(pageable);
        return new ResponseEntity<>(softwarePage,HttpStatus.OK);
    }
    

    public void updateState(Long id){
        softwareRepository.updateState(id);
    }
    
    public void updateName(Long id,String name){
        softwareRepository.updateName(name, id);
    }
    public void updatePhoto(MultipartFile photo,Long id) throws IOException{
        softwareRepository.updatePhoto(Utilitaire.convertMultipartFileToBase64(photo), id);;
    }


    public ResponseEntity<?> search(String search){
        List<Software> getAllBySearchName = softwareRepository.searchByName(search);
        return new ResponseEntity(getAllBySearchName,HttpStatus.OK);
    }

}
