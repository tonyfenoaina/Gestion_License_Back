package com.licence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.licence.models.Module;
import com.licence.repository.ModuleRepository;
import com.licence.util.Utilitaire;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class ModuleService {
    

    @Autowired
    @Lazy
    private ModuleRepository moduleRepository;

    @Autowired 
    @Lazy
    private SoftwareService softwareService; 

    public ResponseEntity<?> save(String name,long id_software){
        Module module = new Module();
        module.setName(name);
        module.setSoftware(softwareService.getByID(id_software));
        module.setPhoto(null);
        module.setState(1);
        moduleRepository.save(module);
        return new ResponseEntity<>(module,HttpStatus.OK);
    }

    public ResponseEntity<?> getAll(Long softwareID,int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Module> modulePage = moduleRepository.findAll(softwareID,pageable);
        System.out.print("tonga eto"+softwareID);
        return new ResponseEntity<>(modulePage,HttpStatus.OK);
    }

    public Module getByID(Long id){
        Optional<Module> module = moduleRepository.findById(id);
        return module.orElse(null);
    }

    public void updateState(Long id){
        moduleRepository.updateState(id);
    }

    public void updateName(Long id,String name){
        moduleRepository.updateName(name, id);
    }
    public void updatePhoto(MultipartFile photo,Long id) throws IOException{
        moduleRepository.updatePhoto(Utilitaire.convertMultipartFileToBase64(photo), id);;
    }

    public ResponseEntity<?> search(String search,Long idSoftware){
        List<Module> searchByName = moduleRepository.searchByName(search,idSoftware);
        return new ResponseEntity<>(searchByName,HttpStatus.OK);
    }

}
