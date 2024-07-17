package com.bernmpdev.javerpersistenceservice.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long telefone;
    private Boolean correntista;

    @Column(name = "score_credito")
    private Float scoreCredito;

    @Column(name = "saldo_cc")
    private Float saldoCc;

    public void updateFrom(CustomerEntity customer) {
        this.nome = customer.getNome();
        this.telefone = customer.getTelefone();
        this.correntista = customer.getCorrentista();
        this.scoreCredito = customer.getScoreCredito();
        this.saldoCc = customer.getSaldoCc();
    }
}
