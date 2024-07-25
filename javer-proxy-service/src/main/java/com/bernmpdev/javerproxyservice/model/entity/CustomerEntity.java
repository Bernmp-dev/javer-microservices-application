package com.bernmpdev.javerproxyservice.model.entity;

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
    private String nome;
    private String cpf;
    private Long telefone;
    private Boolean correntista;
    private Float scoreCredito;
    private Float saldoCc;
}
