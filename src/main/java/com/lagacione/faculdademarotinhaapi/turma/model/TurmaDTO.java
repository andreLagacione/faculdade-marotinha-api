package com.lagacione.faculdademarotinhaapi.turma.model;

import com.lagacione.faculdademarotinhaapi.turma.enums.Periodo;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class TurmaDTO {
    private Integer id;

    @NotEmpty(message = "Informe o ano!")
    private Integer ano;

    @NotEmpty(message = "Informe o curso!")
    private Integer curso;

    @NotEmpty(message = "Informe o professor!")
    private Integer professor;

    @NotEmpty(message = "Informe ao menos um aluno!")
    private List<Integer> alunos = new ArrayList<>();

    @NotEmpty(message = "Informe o periodo!")
    private Periodo periodo;

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

    public List<Integer> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Integer> alunos) {
        this.alunos = alunos;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
