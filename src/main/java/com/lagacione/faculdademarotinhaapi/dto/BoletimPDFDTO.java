package com.lagacione.faculdademarotinhaapi.dto;

import java.util.ArrayList;
import java.util.List;

public class BoletimPDFDTO {
    private Integer ano;
    private String professor;
    private String aluno;
    private String curso;
    private List<MateriaNotaBimestreDTO> notas = new ArrayList<>();

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

    public List<MateriaNotaBimestreDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<MateriaNotaBimestreDTO> notas) {
        this.notas = notas;
    }

    public static BoletimPDFDTO of(BoletimDTO boletimDTO) {
        BoletimPDFDTO boletimPDFDTO = new BoletimPDFDTO();
        boletimPDFDTO.setAno(boletimDTO.getAno());
        boletimPDFDTO.setProfessor(boletimDTO.getProfessor().getName());
        boletimPDFDTO.setAluno(boletimDTO.getAluno().getName());
        boletimPDFDTO.setCurso(boletimDTO.getCurso().getName());
        boletimPDFDTO.setNotas(boletimDTO.getNotas());
        return boletimPDFDTO;
    }
}
