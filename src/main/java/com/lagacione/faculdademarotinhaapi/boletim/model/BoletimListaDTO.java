package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;

public class BoletimListaDTO {
    private Integer id;
    private Integer ano;
    private String nomeAluno;
    private String nomeProfessor;
    private String nomeCurso;

    public BoletimListaDTO() {}

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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public static BoletimListaDTO of(Boletim boletim) {
        BoletimListaDTO boletimListaDTO = new BoletimListaDTO();
        boletimListaDTO.setId(boletim.getId());
        boletimListaDTO.setAno(boletim.getAno());
        boletimListaDTO.setNomeAluno(boletim.getAluno().getName());
        boletimListaDTO.setNomeProfessor(boletim.getProfessor().getName());
        boletimListaDTO.setNomeCurso(boletim.getCurso().getName());
        return boletimListaDTO;
    }
}
