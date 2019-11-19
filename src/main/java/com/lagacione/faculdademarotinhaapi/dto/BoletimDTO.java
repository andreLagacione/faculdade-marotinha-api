package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Boletim;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoletimDTO {
    private Integer id;

    @NotNull(message = "Informe o professor!")
    private ProfessorDTO professor;

    @NotNull(message = "Informe o aluno!")
    private AlunoDTO aluno;

    @NotNull(message = "Informe uma matéria e a nota")
    private List<MateriaNotaDTO> materiaNotas = new ArrayList<>();

    @NotNull(message = "Informe um bimestre")
    private BimestreDTO bimestre;

    public BoletimDTO() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
    }

    public AlunoDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    public List<MateriaNotaDTO> getMateriaNotas() {
        return materiaNotas;
    }

    public void setMateriaNotas(List<MateriaNotaDTO> materiaNotas) {
        this.materiaNotas = materiaNotas;
    }

    public BimestreDTO getBimestre() {
        return bimestre;
    }

    public void setBimestre(BimestreDTO bimestre) {
        this.bimestre = bimestre;
    }

    public static BoletimDTO of(Boletim boletim) {
        BoletimDTO boletimDTO = new BoletimDTO();
        boletimDTO.setId(boletim.getId());
        boletimDTO.setProfessor(ProfessorDTO.of(boletim.getProfessor()));
        boletimDTO.setAluno(AlunoDTO.of(boletim.getAluno()));
        List<MateriaNotaDTO> materiaNotas = boletim.getMateriaNotas().stream().map(MateriaNotaDTO::of).collect(Collectors.toList());
        boletimDTO.setMateriaNotas(materiaNotas);
        boletimDTO.setBimestre(BimestreDTO.of(boletim.getBimestre()));
        return boletimDTO;
    }
}
