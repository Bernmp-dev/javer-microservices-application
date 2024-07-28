package com.bernmpdev.javerpersistenceservice.service;

import com.bernmpdev.javerpersistenceservice.exception.CustomerAlreadyExistsException;
import com.bernmpdev.javerpersistenceservice.exception.CustomerNotFoundException;
import com.bernmpdev.javerpersistenceservice.model.dto.CustomerDto;
import com.bernmpdev.javerpersistenceservice.model.entity.CustomerEntity;
import com.bernmpdev.javerpersistenceservice.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(CustomerEntity::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(CustomerNotFoundException::new)
                .toDto();
    }

    public CustomerDto createCustomer(CustomerDto customer) {
        if (customerRepository.existsByCpf(customer.cpf())) {
            throw new CustomerAlreadyExistsException();
        }

        return customerRepository
                .save(customer.toEntity())
                .toDto();
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customer) {
        CustomerEntity existingCustomer = customerRepository
                .findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        existingCustomer.updateFrom(customer);

        return customerRepository
                .save(existingCustomer)
                .toDto();
    }

    public void deleteCustomer(Long id) {
        customerRepository
                .findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        customerRepository.deleteById(id);
    }

    public Float getCreditScore(Long id) {
        CustomerEntity customer = customerRepository
                .findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        return customer.getScoreCredito();
    }
}
