package com.bernmpdev.javerproxyservice.mock;

import com.bernmpdev.javerproxyservice.model.entity.CustomerEntity;

import java.util.List;

public class CustomerMock {

    public static CustomerEntity createCustomerEntity() {
        return new CustomerEntity(
                1L,
                "ValidName",
                "12345678912",
                12345678901L,
                true,
                100F,
                1000F
        );
    }

    public static CustomerEntity createAnotherCustomerEntity() {
        return new CustomerEntity(
                1L,
                "AnotherValidName",
                "10987654321",
                12345678901L,
                true,
                100F,
                1000F
        );
    }

    public static List<CustomerEntity> createCustomerEntityList() {
        return List.of(
                createCustomerEntity(),
                createAnotherCustomerEntity()
        );
    }

    public static Object createCustomerEntityWithoutNome() {
        return new CustomerEntity(
                1L,
                "",
                "10987654321",
                12345678901L,
                true,
                100F,
                1000F
        );
    }
}
