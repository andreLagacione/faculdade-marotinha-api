package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.nota.model.NotaPDFDTO;

import java.util.ArrayList;
import java.util.List;

public class BoletimPDFDTO {
    private Integer ano;
    private String professor;
    private String aluno;
    private String turma;
    private List<NotaPDFDTO> notas = new ArrayList<>();

    public BoletimPDFDTO() {}

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public List<NotaPDFDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaPDFDTO> notas) {
        this.notas = notas;
    }

}
