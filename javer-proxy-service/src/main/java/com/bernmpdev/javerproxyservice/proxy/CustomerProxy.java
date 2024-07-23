package com.bernmpdev.javerproxyservice.proxy;

import com.bernmpdev.javerproxyservice.authorization.AuthorizationConfig;
import com.bernmpdev.javerproxyservice.model.entity.CustomerEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "javer-persistence-service", configuration = AuthorizationConfig.class)
public interface CustomerProxy {

    @GetMapping("/customer")
    List<CustomerEntity> getAllCustomers();

    @GetMapping("/customer/{id}")
    CustomerEntity getCustomerById(@PathVariable Long id);

    @PostMapping("/customer")
    CustomerEntity createCustomer(@RequestBody CustomerEntity customer);

    @PutMapping("/customer/{id}")
    CustomerEntity updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer);

    @DeleteMapping("/customer/{id}")
    void deleteCustomer(@PathVariable Long id);

    @GetMapping("/customer/{id}/calculateCreditScore")
    Float calculateCreditScore(@PathVariable Long id);
}