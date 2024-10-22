package com.licence.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.licence.dto.CustomerDto;
import com.licence.models.Customer;
import com.licence.models.User;
import com.licence.repository.CustomerRepository;
import java.util.Optional;


@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    @Lazy
    UserService userService;

    public ResponseEntity<?> getAll(int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return new ResponseEntity<>(customerPage,HttpStatus.OK);
    }

    public List<Customer> getByUser(String token){
        User user = userService.getUserByToken(token);
        return customerRepository.findByUser(user);
    }

    public ResponseEntity<?> advancedSearch(String keywords, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> custPage = customerRepository.advancedSearch(keywords, pageable);
        return new ResponseEntity<>(custPage, HttpStatus.OK);
    }

    public ResponseEntity<?> add(CustomerDto customerDto,String authorizationToken){
        User user = userService.getUserByToken(authorizationToken);
        Customer customer = customerDto.getCustomer();
        customer.setUser(user);
        customerRepository.save(customer);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long idCustomer,CustomerDto customerDto,String authorizationToken){
        User user = userService.getUserByToken(authorizationToken);
        Customer customer = customerDto.getCustomer();
        customer.setId(idCustomer);
        customer.setUser(user);
        customerRepository.save(customer);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

    public void delete(Long idCustomer){
        customerRepository.updateState(idCustomer);
    }

    public Customer getById(Long idCustomer){
        Optional<Customer> customer = customerRepository.findById(idCustomer);
        return customer.orElse(null);
    }

    public void addListInList(List<Customer> grandList,List<Customer> miniList){
        for (Customer customer : miniList) {
            grandList.add(customer);
        }
    }

    public boolean containtsCustomerWithId(List<Customer> list,Long id){
        for (Customer customer : list) {
            if (customer.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<?> search(String search){

        List<Customer> value = new ArrayList();

        List<Customer> customersBySurname = customerRepository.searchBySurname(search);
        List<Customer> customersByFirstname = customerRepository.searchByFirstname(search);
        List<Customer> customersByEmail = customerRepository.searchByEmail(search);

        List<Customer> list = new ArrayList<>();

        addListInList(list, customersBySurname);
        addListInList(list, customersByFirstname);
        addListInList(list, customersByEmail);

        for (Customer customer : list) {
            if (!containtsCustomerWithId(value, customer.getId())) {
                value.add(customer);
            }
        }
    
        return new ResponseEntity<>(value,HttpStatus.OK);
    }
    
}
