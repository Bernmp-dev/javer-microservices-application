package com.bernmpdev.javerpersistenceservice.repository;

import com.bernmpdev.javerpersistenceservice.mock.CustomerMock;
import com.bernmpdev.javerpersistenceservice.model.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("[REPOSITORY] - Customer Repository Tests")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("01 - Return true when CPF exist")
    public void whenExistsByCpf_thenReturnTrue() {
        customerRepository.save(CustomerMock.createCustomerEntity());

        Boolean found = customerRepository.existsByCpf("12345678901");

        assertThat(found).isEqualTo(true);
    }

    @Test
    @DisplayName("02 - Return false when CPF does not exist")
    public void whenCpfDoesNotExist_thenReturnFalse() {
        customerRepository.save(CustomerMock.createCustomerEntity());

        Boolean found = customerRepository.existsByCpf("0");

        assertThat(found).isEqualTo(false);
    }
}
