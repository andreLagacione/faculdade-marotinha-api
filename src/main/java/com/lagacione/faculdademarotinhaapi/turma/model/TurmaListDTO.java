package com.lagacione.faculdademarotinhaapi.turma.model;

import com.lagacione.faculdademarotinhaapi.turma.enums.Periodo;

public class TurmaListDTO {
    private Integer id;
    private Integer ano;
    private String curso;
    private String professor;
    private Integer totalAlunos;
    private Periodo periodo;

    public TurmaListDTO() {}

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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Integer getTotalAlunos() {
        return totalAlunos;
    }

    public void setTotalAlunos(Integer totalAlunos) {
        this.totalAlunos = totalAlunos;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
