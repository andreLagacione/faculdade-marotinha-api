package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaBimestrePDFDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static BoletimPDFDTO of(BoletimDTO boletimDTO) {
        BoletimPDFDTO boletimPDFDTO = new BoletimPDFDTO();
        boletimPDFDTO.setAno(boletimDTO.getAno());
        boletimPDFDTO.setProfessor(boletimDTO.getProfessor().getName());
        boletimPDFDTO.setAluno(boletimDTO.getAluno().getName());
        boletimPDFDTO.setCurso(boletimDTO.getCurso().getName());
        List<MateriaNotaBimestrePDFDTO> notas = boletimDTO.getNotas().stream().map(MateriaNotaBimestrePDFDTO::of).collect(Collectors.toList());
        boletimPDFDTO.setNotas(notas);
        return boletimPDFDTO;
    }
}
