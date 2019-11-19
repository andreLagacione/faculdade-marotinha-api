package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Boletim;

public class BoletimListaDTO {
    private Integer id;
    private String professor;
    private String aluno;
    private String bimestre;

    public BoletimListaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBimestre() {
        return bimestre;
    }

    public void setBimestre(String bimestre) {
        this.bimestre = bimestre;
    }

    public static BoletimListaDTO of(Boletim boletim) {
        BoletimListaDTO boletimListaDTO = new BoletimListaDTO();
        boletimListaDTO.setId(boletim.getId());
        boletimListaDTO.setProfessor(boletim.getProfessor().getName());
        boletimListaDTO.setAluno(boletim.getAluno().getName());

        String bimestre = Integer.toString(boletim.getBimestre().getBimestre());
        String ano = Integer.toString(boletim.getBimestre().getAno());

        boletimListaDTO.setBimestre(bimestre + "/" + ano);
        return boletimListaDTO;
    }
}
