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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("[SERVICE] - Delete customer tests")
public class DeleteCustomerTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("01 - Delete customer - Success")
    void deleteCustomer_Success() {
        CustomerEntity customerEntity = CustomerMock.createCustomerEntity();

        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerEntity));

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("02 - Delete customer - Customer not found")
    void deleteCustomer_CustomerNotFound() {
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1L));

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).deleteById(anyLong());
    }

    @Test
    @DisplayName("03 - Delete customer - Exception Handling")
    void deleteCustomer_ExceptionHandling() {
        Mockito.when(customerRepository.findById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> customerService.deleteCustomer(1L));

        verify(customerRepository, times(1)).findById(anyLong());
        verify(customerRepository, times(0)).deleteById(anyLong());
    }
}
