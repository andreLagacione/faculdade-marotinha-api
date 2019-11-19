package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Boletim;

import java.util.List;
import java.util.stream.Collectors;

public class BoletimListaDTO {
    private Integer id;
    private String professor;
    private String aluno;
    private String bimestre;
    private List<ListaNotaBoletimDTO> notas;

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

    public List<ListaNotaBoletimDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<ListaNotaBoletimDTO> notas) {
        this.notas = notas;
    }

    public static BoletimListaDTO of(BoletimDTO boletim) {
        BoletimListaDTO boletimListaDTO = new BoletimListaDTO();
        boletimListaDTO.setId(boletim.getId());
        boletimListaDTO.setProfessor(boletim.getProfessor().getName());
        boletimListaDTO.setAluno(boletim.getAluno().getName());

        List<ListaNotaBoletimDTO> notas = boletim.getMateriaNotas().stream().map(ListaNotaBoletimDTO::of).collect(Collectors.toList());

//        String bimestre = Integer.toString(notas.get(0).getBimestre().getBimestre());
//        String ano = Integer.toString(notas.get(0).getBimestre().getAno());

        boletimListaDTO.setBimestre("/");
        boletimListaDTO.setNotas(notas);
        return boletimListaDTO;
    }
}
