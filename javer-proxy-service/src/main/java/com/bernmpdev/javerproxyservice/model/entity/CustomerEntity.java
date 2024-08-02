package com.bernmpdev.javerproxyservice.model.entity;

import java.util.Objects;

public class CustomerEntity {

    private Long id;
    private String nome;
    private String cpf;
    private Long telefone;
    private Boolean correntista;
    private Float scoreCredito;
    private Float saldoCc;

    public CustomerEntity() {
    }

    public CustomerEntity(Long id, String nome, String cpf, Long telefone, Boolean correntista, Float scoreCredito, Float saldoCc) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.correntista = correntista;
        this.scoreCredito = scoreCredito;
        this.saldoCc = saldoCc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Boolean getCorrentista() {
        return correntista;
    }

    public void setCorrentista(Boolean correntista) {
        this.correntista = correntista;
    }

    public Float getScoreCredito() {
        return scoreCredito;
    }

    public void setScoreCredito(Float scoreCredito) {
        this.scoreCredito = scoreCredito;
    }

    public Float getSaldoCc() {
        return saldoCc;
    }

    public void setSaldoCc(Float saldoCc) {
        this.saldoCc = saldoCc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(cpf, that.cpf) && Objects.equals(telefone, that.telefone) && Objects.equals(correntista, that.correntista) && Objects.equals(scoreCredito, that.scoreCredito) && Objects.equals(saldoCc, that.saldoCc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, telefone, correntista, scoreCredito, saldoCc);
    }
}
