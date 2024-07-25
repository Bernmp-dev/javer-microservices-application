package com.bernmpdev.javerpersistenceservice.model.entity;


import com.bernmpdev.javerpersistenceservice.model.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Customer")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private Long telefone;
    private Boolean correntista;

    @Column(name = "score_credito")
    private Float scoreCredito;

    @Column(name = "saldo_cc")
    private Float saldoCc;

    public CustomerEntity(
            String nome,
            String cpf,
            Long telefone,
            Boolean correntista,
            Float saldoCc
    ) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.correntista = correntista;
        this.scoreCredito = saldoCc * 0.1f;
        this.saldoCc = saldoCc;
    }

    public void updateFrom(CustomerDto customer) {
        this.nome = customer.nome();
        this.cpf = customer.cpf();
        this.telefone = customer.telefone();
        this.correntista = customer.correntista();
        this.scoreCredito = customer.scoreCredito();
        this.saldoCc = customer.saldoCc();
    }

    public CustomerDto toDto() {
        return new CustomerDto(
                this.id,
                this.nome,
                this.cpf,
                this.telefone,
                this.correntista,
                this.scoreCredito,
                this.saldoCc
        );
    }
}
