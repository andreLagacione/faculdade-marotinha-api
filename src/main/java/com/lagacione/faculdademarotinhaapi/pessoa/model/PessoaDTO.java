package com.lagacione.faculdademarotinhaapi.pessoa.model;

import com.lagacione.faculdademarotinhaapi.commons.validations.cpfCnpj.CpfCnpj;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PessoaDTO {

    @NotEmpty(message = "Informe o nome!")
    @Length(min = 3, max = 50, message = "O tamanho tem que estar entre 3 e 50 caractéres!")
    private String name;

    @NotNull(message = "Informe a idade!")
    private Integer age;

    @NotEmpty(message = "Informe o CPF!")
    @CpfCnpj(message = "O CPF informado não é valido!")
    private String cpf;

    @NotNull(message = "Informe o telefone!")
    private Long phone;

    public PessoaDTO() {}

    public PessoaDTO(String name, Integer age, String cpf, Long phone) {
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
