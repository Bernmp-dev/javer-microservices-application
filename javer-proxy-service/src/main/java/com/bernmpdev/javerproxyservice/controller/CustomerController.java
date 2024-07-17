package com.bernmpdev.javerproxyservice.controller;

import com.bernmpdev.javerproxyservice.model.entity.CustomerEntity;
import com.bernmpdev.javerproxyservice.proxy.CustomerProxy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

    @Autowired
    private CustomerProxy customerProxy;

    @GetMapping
    public List<CustomerEntity> getAllCustomers() {
        return customerProxy.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomerById(
            @PathVariable Long id
    ) {
        return customerProxy.getCustomerById(id);
    }

    @PostMapping
    public CustomerEntity createCustomer(
            @Valid @RequestBody CustomerEntity customer
    ) {
        return customerProxy.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public CustomerEntity updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerEntity customer
    ) {
        return customerProxy.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerProxy.deleteCustomer(id);
    }

    @GetMapping("/{id}/calculateCreditScore")
    public Float calculateCreditScore(@PathVariable Long id) {
        return customerProxy.calculateCreditScore(id);
    }
}