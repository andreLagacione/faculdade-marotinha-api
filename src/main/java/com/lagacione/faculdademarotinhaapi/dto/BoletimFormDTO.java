package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Boletim;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoletimFormDTO {
    private Integer id;
    private Integer professor;
    private Integer aluno;
    private Integer bimestre;
    private List<ListaNotaIntoBoletimDTO> notas = new ArrayList<>();

    public BoletimFormDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfessor() {
        return professor;
    }

    public void setProfessor(Integer professor) {
        this.professor = professor;
    }

    public Integer getAluno() {
        return aluno;
    }

    public void setAluno(Integer aluno) {
        this.aluno = aluno;
    }

    public Integer getBimestre() {
        return bimestre;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public List<ListaNotaIntoBoletimDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<ListaNotaIntoBoletimDTO> notas) {
        this.notas = notas;
    }

    public static BoletimFormDTO of(Boletim boletim) {
        BoletimFormDTO boletimFormDTO = new BoletimFormDTO();
        boletimFormDTO.setId(boletim.getId());
        boletimFormDTO.setProfessor(boletim.getProfessor().getId());
        boletimFormDTO.setAluno(boletim.getAluno().getId());
        boletimFormDTO.setBimestre(boletim.getBimestre().getId());

        List<ListaNotaIntoBoletimDTO> notas = boletim.getMateriaNotas().stream().map(ListaNotaIntoBoletimDTO::of).collect(Collectors.toList());

        boletimFormDTO.setNotas(notas);

        return boletimFormDTO;
    }
}
