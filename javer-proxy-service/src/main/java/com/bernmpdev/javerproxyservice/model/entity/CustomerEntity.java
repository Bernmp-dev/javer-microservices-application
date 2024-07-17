package com.bernmpdev.javerproxyservice.model.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerEntity {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Telefone é obrigatório")
    @Digits(integer = 11, fraction = 0, message = "Telefone deve conter 10 ou 11 dígitos")
    @Min(value = 100000000L, message = "Telefone deve ser um número válido")
    private Long telefone;

    @NotNull(message = "Campo Correntista é obrigatório")
    private Boolean correntista;

    @NotNull(message = "Score de crédito é obrigatório")
    @Min(value = 0, message = "Score de crédito deve ser maior ou igual a zero")
    private Float scoreCredito;

    @NotNull(message = "Saldo da conta corrente é obrigatório")
    @Min(value = 0, message = "Saldo da conta corrente deve ser maior ou igual a zero")
    private Float saldoCc;
}
