package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Curso;

import javax.validation.constraints.NotNull;

public class AlunoDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe um curso")
    private Curso curso;

    public AlunoDTO() {}

    public AlunoDTO(String name, Integer age, String cpf, Long phone, Integer id, Curso curso) {
        super(name, age, cpf, phone);
        this.id = id;
        this.curso = curso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
