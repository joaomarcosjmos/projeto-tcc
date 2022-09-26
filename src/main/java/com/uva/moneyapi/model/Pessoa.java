package com.uva.moneyapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Size(min = 3, max = 30)
    private String nome;

    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Pessoa() {
    }

    public Pessoa(String nome, boolean ativo) {
        this.nome = nome;
        this.ativo = ativo;
    }

    @JsonIgnore
    @Transient
    public boolean isInativo() {
        return !this.ativo;
    }
}
