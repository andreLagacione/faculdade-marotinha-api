package com.lagacione.faculdademarotinhaapi.pessoa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class Pessoa {

    private String name;
    private Integer age;
    private String cpf;
    private Long phone;

    public Pessoa(String name, Integer age, String cpf, Long phone) {
        this.name = name;
        this.age = age;
        this.cpf = cpf;
        this.phone = phone;
    }

}
