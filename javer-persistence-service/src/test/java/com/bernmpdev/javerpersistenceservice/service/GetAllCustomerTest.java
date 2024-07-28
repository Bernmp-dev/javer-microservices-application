package com.bernmpdev.javerpersistenceservice.service;

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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("[SERVICE] - Get all customers tests")
public class GetAllCustomerTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("01 - Get all customers - Success")
    void getAllCustomer_Success() {

        var customerEntityList = CustomerMock.CustomerEntityList();

        Mockito
                .when(customerRepository.findAll())
                .thenReturn(customerEntityList);

        var result = customerService.getAllCustomers();

        Mockito.verify(
                customerRepository,
                Mockito.times(1))
                .findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(customerEntityList.size());
        assertThat(result).isEqualTo(CustomerMock.CustomerDtoList());
    }

    @Test
    @DisplayName("02 - Get all customers - Empty List")
    void getAllCustomers_EmptyList() {
        Mockito
                .when(customerRepository.findAll())
                .thenReturn(Collections.emptyList());

        var result = customerService.getAllCustomers();

        Mockito.verify(
                        customerRepository,
                        Mockito.times(1))
                .findAll();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("03 - Get all customers - Exception Handling")
    void getAllCustomers_ExceptionHandling() {
        Mockito
                .when(customerRepository.findAll())
                .thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            customerService.getAllCustomers();
        });

        Mockito.verify(
                        customerRepository,
                        Mockito.times(1))
                .findAll();
    }

    @Test
    @DisplayName("04 - Get all customers - Null Fields Handling")
    void getAllCustomers_NullFieldsHandling() {

        List<CustomerEntity> customerEntityList = CustomerMock.CustomerEntityList();
        customerEntityList.getFirst().setNome(null);
        customerEntityList.getFirst().setTelefone(null);
        customerEntityList.getFirst().setCorrentista(null);

        Mockito
                .when(customerRepository.findAll())
                .thenReturn(customerEntityList);

        var result = customerService.getAllCustomers();

        Mockito.verify(
                        customerRepository,
                        Mockito.times(1))
                .findAll();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(customerEntityList.size());
        assertThat(result.getFirst().nome()).isNull();
        assertThat(result.getFirst().telefone()).isNull();
        assertThat(result.getFirst().correntista()).isNull();
    }
}
