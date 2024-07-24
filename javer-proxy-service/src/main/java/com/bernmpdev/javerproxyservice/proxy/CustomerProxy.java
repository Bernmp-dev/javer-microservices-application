package com.bernmpdev.javerproxyservice.proxy;

import com.bernmpdev.javerproxyservice.authorization.AuthorizationConfig;
import com.bernmpdev.javerproxyservice.model.entity.CustomerEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "javer-persistence-service", path = "/customer", configuration = AuthorizationConfig.class)
public interface CustomerProxy {

    @GetMapping
    List<CustomerEntity> getAllCustomers();

    @GetMapping("/{id}")
    CustomerEntity getCustomerById(@PathVariable Long id);

    @PostMapping
    CustomerEntity createCustomer(@RequestBody CustomerEntity customer);

    @PutMapping("/{id}")
    CustomerEntity updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerEntity customer
    );

    @DeleteMapping("/{id}")
    void deleteCustomer(@PathVariable Long id);

    @GetMapping("/{id}/calculateCreditScore")
    Float calculateCreditScore(@PathVariable Long id);
}