package com.bernmpdev.javerpersistenceservice.service;

import com.bernmpdev.javerpersistenceservice.exception.CustomerNotFoundException;
import com.bernmpdev.javerpersistenceservice.mock.CustomerMock;
import com.bernmpdev.javerpersistenceservice.model.entity.CustomerEntity;
import com.bernmpdev.javerpersistenceservice.model.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("[SERVICE] - Get customer by ID tests")
public class GetCustomerByIdTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("01 - Get customer by ID - Success")
    void getCustomerById_Success() {
        CustomerEntity customerEntity = CustomerMock.createCustomerEntity();
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerEntity));

        var result = customerService.getCustomerById(1L);

        Mockito.verify(customerRepository, Mockito.times(1)).findById(anyLong());
        assertThat(result).isNotNull();
        assertThat(result.cpf()).isEqualTo(customerEntity.getCpf());
    }

    @Test
    @DisplayName("02 - Get customer by ID - Not Found")
    void getCustomerById_NotFound() {
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1L));

        Mockito.verify(customerRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("03 - Get customer by ID - Exception Handling")
    void getCustomerById_ExceptionHandling() {
        Mockito.when(customerRepository.findById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(1L));

        Mockito.verify(customerRepository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("04 - Get customer by ID - Null ID")
    void getCustomerById_NullId() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(null));
    }
}
