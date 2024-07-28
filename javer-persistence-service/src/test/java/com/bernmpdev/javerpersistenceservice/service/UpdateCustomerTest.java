package com.bernmpdev.javerpersistenceservice.service;

import com.bernmpdev.javerpersistenceservice.exception.CustomerNotFoundException;
import com.bernmpdev.javerpersistenceservice.mock.CustomerMock;
import com.bernmpdev.javerpersistenceservice.model.dto.CustomerDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("[SERVICE] - Update customer tests")
public class UpdateCustomerTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("01 - Update customer - Success")
    void updateCustomer_Success() {
        CustomerDto customerDto = CustomerMock.createCustomerDto();
        CustomerEntity existingCustomer = CustomerMock.createCustomerEntity();
        CustomerEntity updatedCustomer = customerDto.toEntity();
        updatedCustomer.setId(existingCustomer.getId());

        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        Mockito.when(customerRepository.save(any(CustomerEntity.class))).thenReturn(updatedCustomer);

        CustomerDto result = customerService.updateCustomer(1L, customerDto);

        Mockito.verify(customerRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(customerRepository, Mockito.times(1)).save(any(CustomerEntity.class));
        
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(existingCustomer.getId());
        assertThat(result.cpf()).isEqualTo(customerDto.cpf());
        assertThat(result.nome()).isEqualTo(customerDto.nome());
    }

    @Test
    @DisplayName("02 - Update customer - Not Found")
    void updateCustomer_NotFound() {
        CustomerDto customerDto = CustomerMock.createCustomerDto();

        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1L, customerDto));

        Mockito.verify(customerRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(customerRepository, Mockito.times(0)).save(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("03 - Update customer - Exception Handling")
    void updateCustomer_ExceptionHandling() {
        CustomerDto customerDto = CustomerMock.createCustomerDto();

        Mockito.when(customerRepository.findById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> customerService.updateCustomer(1L, customerDto));

        Mockito.verify(customerRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(customerRepository, Mockito.times(0)).save(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("04 - Update customer - Null ID")
    void updateCustomer_NullId() {
        CustomerDto customerDto = CustomerMock.createCustomerDto();
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(null, customerDto));
    }

    @Test
    @DisplayName("05 - Update customer - Null DTO")
    void updateCustomer_NullDto() {
        CustomerEntity existingCustomer = CustomerMock.createCustomerEntity();

        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));

        assertThrows(NullPointerException.class, () -> customerService.updateCustomer(1L, null));

        Mockito.verify(customerRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(customerRepository, Mockito.times(0)).save(any(CustomerEntity.class));
    }
}
