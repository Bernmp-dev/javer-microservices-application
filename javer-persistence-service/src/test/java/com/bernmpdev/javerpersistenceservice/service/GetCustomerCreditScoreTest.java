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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("[SERVICE] - Get customer credit score tests")
public class GetCustomerCreditScoreTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("01 - Get credit score - Success")
    void getCreditScore_Success() {
        CustomerEntity customerEntity = CustomerMock.createCustomerEntity();

        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerEntity));

        Float creditScore = customerService.getCreditScore(1L);

        verify(customerRepository, times(1)).findById(anyLong());
        assertThat(creditScore).isEqualTo(customerEntity.getScoreCredito());
    }

    @Test
    @DisplayName("02 - Get credit score - Customer not found")
    void getCreditScore_CustomerNotFound() {
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCreditScore(1L));

        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("03 - Get credit score - Exception Handling")
    void getCreditScore_ExceptionHandling() {
        Mockito.when(customerRepository.findById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> customerService.getCreditScore(1L));

        verify(customerRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("04 - Get credit score - Null Credit Score")
    void getCreditScore_NullCreditScore() {
        CustomerEntity customerEntity = CustomerMock.createCustomerEntity();
        customerEntity.setScoreCredito(null);

        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerEntity));

        Float creditScore = customerService.getCreditScore(1L);

        verify(customerRepository, times(1)).findById(anyLong());
        assertThat(creditScore).isNull();
    }
}
