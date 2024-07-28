package com.bernmpdev.javerpersistenceservice.mock;

import com.bernmpdev.javerpersistenceservice.model.dto.CustomerDto;
import com.bernmpdev.javerpersistenceservice.model.entity.CustomerEntity;

import java.util.List;

public class CustomerMock {

    public static CustomerDto createCustomerDto() {
        return new CustomerDto(
                1L,
                "João",
                "12345678901",
                11987654321L,
                true,
                0F,
                1000.0F
        );
    }

    public static CustomerEntity createCustomerEntity() {
        return new CustomerEntity(
                "João",
                "12345678901",
                11987654321L,
                true,
                1000.0F
        );
    }

    public static CustomerDto createCustomerDtoWithoutCpf() {
        return createCustomerDtoWithField("cpf", null);
    }

    public static CustomerDto createCustomerDtoWithoutNome() {
        return createCustomerDtoWithField("nome", null);
    }

    public static CustomerDto createCustomerDtoWithoutTelefone() {
        return createCustomerDtoWithField("telefone", null);
    }

    public static CustomerDto createCustomerDtoWithoutCorrentista() {
        return createCustomerDtoWithField("correntista", null);
    }

    public static CustomerDto createCustomerDtoWithoutSaldoCc() {
        return createCustomerDtoWithField("saldoCc", null);
    }

    public static CustomerDto createCustomerDtoWithShortNome() {
        return createCustomerDtoWithField("nome", "A");
    }

    public static CustomerDto createCustomerDtoWithInvalidNome() {
        return createCustomerDtoWithField("nome", "Joao123");
    }

    public static CustomerDto createCustomerDtoWithInvalidCpf() {
        return createCustomerDtoWithField("cpf", "1234567890");
    }

    public static CustomerDto createCustomerDtoWithShortTelefone() {
        return createCustomerDtoWithField("telefone", 123L);
    }

    public static CustomerDto createCustomerDtoWithNegativeScore() {
        return createCustomerDtoWithField("scoreCredito", -10.0f);
    }

    public static CustomerDto createCustomerDtoWithNegativeSaldoCc() {
        return createCustomerDtoWithField("saldoCc", -500.0f);
    }

    public static List<CustomerEntity> CustomerEntityList() {
        return List.of(
                createCustomerEntity(),
                createCustomerEntity(),
                createCustomerEntity()
        );
    }

    public static List<CustomerDto> CustomerDtoList() {
        return CustomerEntityList()
                .stream()
                .map(CustomerEntity::toDto)
                .toList();
    }

    public static CustomerDto createCustomerDtoWithField(String key, Object value) {
        CustomerDto baseDto = createCustomerDto();
        return switch (key) {
            case "nome" -> new CustomerDto(baseDto.id(), (String) value, baseDto.cpf(), baseDto.telefone(), baseDto.correntista(), baseDto.scoreCredito(), baseDto.saldoCc());
            case "cpf" -> new CustomerDto(baseDto.id(), baseDto.nome(), (String) value, baseDto.telefone(), baseDto.correntista(), baseDto.scoreCredito(), baseDto.saldoCc());
            case "telefone" -> new CustomerDto(baseDto.id(), baseDto.nome(), baseDto.cpf(), (Long) value, baseDto.correntista(), baseDto.scoreCredito(), baseDto.saldoCc());
            case "correntista" -> new CustomerDto(baseDto.id(), baseDto.nome(), baseDto.cpf(), baseDto.telefone(), (Boolean) value, baseDto.scoreCredito(), baseDto.saldoCc());
            case "scoreCredito" -> new CustomerDto(baseDto.id(), baseDto.nome(), baseDto.cpf(), baseDto.telefone(), baseDto.correntista(), (Float) value, baseDto.saldoCc());
            case "saldoCc" -> new CustomerDto(baseDto.id(), baseDto.nome(), baseDto.cpf(), baseDto.telefone(), baseDto.correntista(), baseDto.scoreCredito(), (Float) value);
            default -> baseDto;
        };
    }
}
