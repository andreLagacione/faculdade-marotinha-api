package com.lagacione.faculdademarotinhaapi.turma.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class TurmaDTO {
    private Integer id;

    @NotNull(message = "Informe o ano!")
    private Integer ano;

    @NotNull(message = "Informe o curso!")
    private Integer curso;

    @NotNull(message = "Informe o professor!")
    private Integer professor;

    @NotEmpty(message = "Informe o periodo!")
    private String periodo;

    public TurmaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public Integer getProfessor() {
        return professor;
    }

    public void setProfessor(Integer professor) {
        this.professor = professor;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
