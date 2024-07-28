package com.bernmpdev.javerpersistenceservice.service;

import com.bernmpdev.javerpersistenceservice.exception.CustomerAlreadyExistsException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("[SERVICE] - Create customer tests")
public class CreateCustomerTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("01 - Create Customer - Success")
    void testCreateCustomer() {
        CustomerDto customerDto = CustomerMock.createCustomerDto();
        CustomerEntity customerEntity = customerDto.toEntity();

        Mockito
                .when(customerRepository.existsByCpf(customerDto.cpf()))
                .thenReturn(false);

        Mockito
                .when(customerRepository.save(any(CustomerEntity.class)))
                .thenReturn(customerEntity);

        CustomerDto createdCustomer = customerService.createCustomer(customerDto);

        verify(
                customerRepository,
                times(1)
        )
                .save(any(CustomerEntity.class));

        assertThat(createdCustomer)
                .isNotNull();

        assertThat(createdCustomer.cpf())
                .isEqualTo(customerDto.cpf());
    }

    @Test
    @DisplayName("02 - Create Customer - Customer already exists")
    void testCreateCustomerAlreadyExists() {
        CustomerDto customerDto = CustomerMock.createCustomerDto();

        Mockito.when(customerRepository
                .existsByCpf(customerDto.cpf()))
                .thenReturn(true);

        assertThrows(CustomerAlreadyExistsException.class, () ->
                customerService.createCustomer(customerDto));

        verify(
                customerRepository,
                times(0))
                .save(any(CustomerEntity.class)
                );
    }

    @Test
    @DisplayName("03 - Create Customer - All Attributes are Correctly Saved")
    void testCreateCustomerAllAttributes() {
        CustomerDto customerDto = CustomerMock.createCustomerDto();
        CustomerEntity customerEntity = customerDto.toEntity();

        Mockito.when(customerRepository.existsByCpf(customerDto.cpf()))
                .thenReturn(false);

        Mockito.when(customerRepository.save(any(CustomerEntity.class)))
                .thenReturn(customerEntity);

        CustomerDto createdCustomer = customerService.createCustomer(customerDto);

        verify(
                customerRepository,
                times(1)
        )
                .save(any(CustomerEntity.class));

        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.cpf()).isEqualTo(customerDto.cpf());
        assertThat(createdCustomer.nome()).isEqualTo(customerDto.nome());
        assertThat(createdCustomer.telefone()).isEqualTo(customerDto.telefone());
        assertThat(createdCustomer.correntista()).isEqualTo(customerDto.correntista());
        assertThat(createdCustomer.scoreCredito()).isEqualTo(customerDto.saldoCc() * 0.1f);
        assertThat(createdCustomer.saldoCc()).isEqualTo(customerDto.saldoCc());
    }
}
