package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestrePDFDTO;

import java.util.ArrayList;
import java.util.List;

public class BoletimPDFDTO {
    private Integer ano;
    private String professor;
    private String aluno;
    private String curso;
    private List<MateriaNotaBimestrePDFDTO> notas = new ArrayList<>();

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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<MateriaNotaBimestrePDFDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<MateriaNotaBimestrePDFDTO> notas) {
        this.notas = notas;
    }

}
