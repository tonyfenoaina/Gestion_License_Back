package com.licence.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.spi.ObjectThreadContextMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.licence.dto.LicenceData;
import com.licence.dto.LicenceDto;
import com.licence.dto.LicenceIdentityDto;
import com.licence.dto.LicenceModuleDto;
import com.licence.models.Customer;
import com.licence.models.Licence;
import com.licence.models.LicenceIdentity;
import com.licence.models.LicenceModule;
import com.licence.models.Module;
import com.licence.models.Software;
import com.licence.repository.LicenceIdentityRepository;
import com.licence.repository.LicenceModuleRepository;
import com.licence.repository.LicenceRepository;
import com.licence.response.Error;

import io.jsonwebtoken.Claims;
import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.LicenseReader;
import javax0.license3j.io.LicenseWriter;;

@Service
public class LicenceService {

    File licence_directory; 
    File licence_file; 

    final String algorithme = "RSA";

    int size = 2048;
    License license;

    @Value("${licence-format}")
    private String format;

    @Value("${licence-digest}")
    private String digest;

    LicenseKeyPair licenseKeyPair;

    @Autowired
    @Lazy
    private CustomerService customerService;

    @Autowired
    @Lazy
    private SoftwareService softwareService;

    @Autowired
    @Lazy
    private ModuleService moduleService;

    @Autowired 
    @Lazy
    private LicenceRepository licenceRepository;

    @Autowired
    @Lazy
    private LicenceModuleRepository licenceModuleRepository;

    @Autowired
    @Lazy
    private LicenceIdentityRepository licenceIdentityRepository;

    @Autowired
    @Lazy
    LicenceTokenService licenceTokenService;


    private static final String HEX_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();


    // Generer un id pc unique
    public String generateUniqueCode() {
        StringBuilder code = new StringBuilder(8);
        
        // Génération de 8 caractères aléatoires hexadécimaux
        for (int i = 0; i < 4; i++) {
            int index = RANDOM.nextInt(HEX_CHARS.length());
            code.append(HEX_CHARS.charAt(index));
        }

        // Retourne le code au format "PC-xxxxxxxx"
        return "PC-" + code.toString().toUpperCase()+System.currentTimeMillis();
    }

    // generer les clé privée et public
    public void generateKeys(String algorithme,int size){
        try {
            licenseKeyPair = LicenseKeyPair.Create.from(algorithme, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // cree une nouvel licence
    public void create_licence(Date expirationDate) throws IOException{
        license = new License();
        license.setLicenseId();
        generateKeys(algorithme, size);
        license.setExpiry(expirationDate);
    }


    // recuperer nouvell licence
    public void getLicence(){
        try {
            license = new LicenseReader(licence_file).read();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // stocker la licence dans le serveur
    public void save_licence() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException{
        if (!(licence_directory.isDirectory() && licence_directory.exists())) {
            licence_directory.mkdirs();
        }
        if (!(licence_file.exists())) {
            licence_file.createNewFile();
        }
        license.sign(licenseKeyPair.getPair().getPrivate(), digest);
        LicenseWriter writer = new LicenseWriter(licence_file);
        writer.write(license,IOFormat.valueOf(format));
        writer.close();
    }
    
    public void save_licence_without_sign() throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException{
        if (!(licence_directory.isDirectory() && licence_directory.exists())) {
            licence_directory.mkdirs();
        }
        if (!(licence_file.exists())) {
            licence_file.createNewFile();
        }
        // license.sign(licenseKeyPair.getPair().getPrivate(), digest);
        LicenseWriter writer = new LicenseWriter(licence_file);
        writer.write(license,IOFormat.valueOf(format));
        writer.close();
    }



    // verifier la licence
    public int licence_verification(byte[] publicKey){
        if (!license.isExpired()) {
            if (license.isOK(publicKey)) {
                return 1;
            }
            return 0;
        }
        return -1;
    }

    
    public  byte[] convertPropertyToBytes(Properties properties) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(properties);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public Properties convertBytesToProperty(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Properties) objectInputStream.readObject();
    }


    public Feature createFeature(String name,Properties properties) throws IOException{
        return Feature.Create.binaryFeature(name, convertPropertyToBytes(properties));
    }


    public void addToLicence(Feature feature){
        license.add(feature);
    }


    public void init(String idPc){
        licence_file = new File("license/"+idPc+"/license.bin");
        licence_directory = new File("license/"+idPc);
    }


    public Licence getLicenceByID(Long id){
        Optional<Licence> licence = licenceRepository.findById(id);
        return licence.orElse(null);
    }


    
    public Licence addLicence(LicenceDto licenceDto){
        Customer customer = customerService.getById(licenceDto.getIdCustomer());
        Software software = softwareService.getByID(licenceDto.getIdSoftware());
        Licence licence = licenceDto.setLicence(customer, software);
        return licenceRepository.save(licence);
    }


    public List<LicenceModule> addModule(LicenceModuleDto licenceModuleDto){
        List<LicenceModule> listLicenceModules = new ArrayList<>();
        Licence licence = getLicenceByID(licenceModuleDto.getIdLicence());
        List<Long> listIdModule = licenceModuleDto.getListIdModule();
        for (Long idModule : listIdModule) {
            Module module = moduleService.getByID(idModule);
            LicenceModule licenceModule = new LicenceModule();
            licenceModule.setLicence(licence);
            licenceModule.setModule(module);
            listLicenceModules.add(licenceModuleRepository.save(licenceModule));
        }
        return listLicenceModules;
    }

    public String byteArrayToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = String.format("%02x", b);  // Format en deux chiffres hexadécimaux
            hexString.append(hex);
        }
        return hexString.toString();
    }
    

    public byte[] hexToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
    

    
    public List<LicenceIdentity> addPc(LicenceIdentityDto licenceIdentityDto) throws Exception{
        List<LicenceIdentity> listLicenceIdentities = new ArrayList<>();
        Licence licence = getLicenceByID(licenceIdentityDto.getIdLicence());
        List<String> listIdPc = licenceIdentityDto.getListIdPc();
        for (String idPc : listIdPc) {
            init(idPc);
            create_licence(licence.getEndDate());
            LicenceIdentity licenceIdentity = new LicenceIdentity();
            licenceIdentity.setIdPc(idPc);
            licenceIdentity.setIdLicence(license.getLicenseId().toString());
            licenceIdentity.setLicence(licence);
            String publicKey = byteArrayToHex(licenseKeyPair.getPublic());
            licenceIdentity.setPublicKey(publicKey);
            licenceIdentity.setModeActivation(0);
            licenceIdentity.setState(0);
            listLicenceIdentities.add(licenceIdentityRepository.save(licenceIdentity));
            save_licence();
        }
        return listLicenceIdentities;
    }
    

    public List<Licence> getAll(){
        return licenceRepository.findAll();
    }

    public ResponseEntity<?> getAllList(int page,int size){
        System.out.println("tonga eto soft");
        Pageable pageable= PageRequest.of(page, size);
        Page<Licence> licencePage = licenceRepository.findAll(pageable);
        return new ResponseEntity<>(licencePage,HttpStatus.OK);
    }

    public LicenceData getDataLicence(Long idLicence){
        LicenceData licenceData = new LicenceData();
        Licence licence = getLicenceByID(idLicence);
        licenceData.setLicence(licence);
        List<Module> listModules = new ArrayList<>();
        List<LicenceModule> licenceModules = licenceModuleRepository.findByLicenceId(idLicence);
        for (LicenceModule licenceModule : licenceModules) {
            listModules.add(licenceModule.getModule());
        }
        licenceData.setModules(listModules);
        licenceData.setLicenceIdentities(licenceIdentityRepository.findByLicence_id(idLicence));
        return licenceData;
    }

    public ResponseEntity<?> getDataLicencePdf(Long idLicence){
        LicenceData licenceData = getDataLicence(idLicence);
        HttpHeaders headers = new HttpHeaders();
        ByteArrayOutputStream byteArrayOutputStream = PdfService.getByteArrayOutputStreamPdf(licenceData);
        byte[] bytes= byteArrayOutputStream.toByteArray();

        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=licence_info.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(bytes.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(byteArrayResource);
    }

    public ResponseEntity<?> sendListIdPc(Long idLicence){
        Licence licence = getLicenceByID(idLicence);
        System.out.println(licence);
        int nombreActivation = licence.getNumberActivation();
        List<String> listIdPc = new ArrayList<>();
        for (int i = 0; i < nombreActivation; i++) {
            listIdPc.add(generateUniqueCode());
        }
        return new ResponseEntity<>(listIdPc,HttpStatus.OK);
    }


    private ResponseEntity<?> isLicenceOK(String idPc,String idLicence,int mode) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, IOException{
        LicenceIdentity licenceIdentity = licenceIdentityRepository.findByIdLicenceAndIdPc(idLicence,idPc);
        if (license==null || licenceIdentity==null) {
            return new ResponseEntity<>("Licence not found",HttpStatus.NOT_FOUND);
        }
        byte[] publicKey = hexToByteArray(licenceIdentity.getPublicKey());
        if (licence_verification(publicKey)==1) {
            licenceIdentity.setState(1);
            licenceIdentity.setModeActivation(mode);
            licenceIdentityRepository.save(licenceIdentity);
            String token = licenceTokenService.generateToken(licenceIdentity);
            Feature dateFeature = Feature.Create.dateFeature("date_activation", new Date());
            addToLicence(dateFeature);
            Feature tokenFeature = Feature.Create.stringFeature("token", token);
            addToLicence(tokenFeature);
            save_licence_without_sign();
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(licence_verification(publicKey)==0 ? "Code incorrect" : "Date expired",HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> activeLicence(String idPc,String idLicence,int mode) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, IOException{
        init(idPc);
        getLicence();
        System.out.println(license);
        Feature feature = license.get("token");
        if (feature==null) {
            return isLicenceOK(idPc, idLicence, mode);
        }
        else{
            return new ResponseEntity<>(Error.init("Licence deja active"),HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> getLicenceByToken(String token) throws JsonMappingException, JsonProcessingException{
        Object licence = licenceTokenService.extractAllClaims(token).get("licence");
        return new ResponseEntity<>(licence,HttpStatus.OK);
    }

    public ResponseEntity<?> getAllLicenceByCustomer(Long idCustomer){
        
        List<Licence> licences = licenceRepository.findByUserId(idCustomer);
        return new ResponseEntity<>(licences,HttpStatus.OK);
    }

}
