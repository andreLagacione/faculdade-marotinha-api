package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Aluno;
import com.lagacione.faculdademarotinhaapi.domain.Bimestre;
import com.lagacione.faculdademarotinhaapi.domain.Boletim;
import com.lagacione.faculdademarotinhaapi.domain.Professor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoletimDTO {
    private Integer id;

    @NotNull(message = "Informe o professor!")
    private Professor professor;

    @NotNull(message = "Informe o aluno!")
    private Aluno aluno;

    @NotNull(message = "Informe uma matéria e a nota")
    private List<MateriaNotaDTO> materiaNotas = new ArrayList<>();

    @NotNull(message = "Informe um bimestre")
    private Bimestre bimestre;

    public BoletimDTO() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<MateriaNotaDTO> getMateriaNotas() {
        return materiaNotas;
    }

    public void setMateriaNotas(List<MateriaNotaDTO> materiaNotas) {
        this.materiaNotas = materiaNotas;
    }

    public Bimestre getBimestre() {
        return bimestre;
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre;
    }

    public static BoletimDTO of(Boletim boletim) {
        BoletimDTO boletimDTO = new BoletimDTO();
        boletimDTO.setId(boletim.getId());
        boletimDTO.setProfessor(boletim.getProfessor());
        boletimDTO.setAluno(boletim.getAluno());
        List<MateriaNotaDTO> materiaNotas = boletim.getMateriaNotas().stream().map(MateriaNotaDTO::of).collect(Collectors.toList());
        boletimDTO.setMateriaNotas(materiaNotas);
        boletimDTO.setBimestre(boletim.getBimestre());
        return boletimDTO;
    }
}
