package com.bernmpdev.javerpersistenceservice.model.dto;

import jakarta.validation.constraints.*;
import com.bernmpdev.javerpersistenceservice.model.entity.CustomerEntity;

public record CustomerDto(
        Long id,

        @NotNull(message = "Nome é obrigatório")
        @Size(min = 2, max = 150, message = "Nome deve ter entre {min} e {max} carácteres")
        @Pattern(regexp = "^[A-Za-zÀ-ú]+$", message = "Nome deve conter apenas letras")
        String nome,

        @NotNull(message = "CPF é obrigatório")
        @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve ter 11 dígitos")
        String cpf,

        @NotNull(message = "Telefone é obrigatório")
        @Digits(integer = 11, fraction = 0, message = "Telefone deve conter 10 ou 11 dígitos")
        @Min(value = 100000000L, message = "Telefone deve ser um número válido")
        Long telefone,

        @NotNull(message = "Correntista é obrigatório")
        Boolean correntista,

        @NotNull(message = "Score de crédito é obrigatório")
        @Min(value = 0, message = "Score de crédito deve ser maior ou igual a zero")
        Float scoreCredito,

        @NotNull(message = "Saldo da conta corrente é obrigatório")
        @Min(value = 0, message = "Saldo da conta corrente deve ser maior ou igual a zero")
        Float saldoCc
) {
        public CustomerEntity toEntity() {
            return new CustomerEntity(
                        this.nome,
                        this.cpf,
                        this.telefone,
                        this.correntista,
                        this.saldoCc
                );
        }
}
