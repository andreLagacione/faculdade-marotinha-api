package com.lagacione.faculdademarotinhaapi.pessoa.entity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Pessoa {
    private String name;
    private Integer age;
    private String cpf;
    private Long phone;

    public Pessoa() {}

    public Pessoa(String name, Integer age, String cpf, Long phone) {
        this.name = name;
        this.age = age;
        this.cpf = cpf;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
