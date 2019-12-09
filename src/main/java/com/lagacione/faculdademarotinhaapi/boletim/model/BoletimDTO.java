package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.dto.CursoDTO;
import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaBimestreDTO;
import com.lagacione.faculdademarotinhaapi.dto.ProfessorDTO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoletimDTO {
    private Integer id;

    @NotNull(message = "Informe o ano!")
    private Integer ano;

    @NotNull(message = "Informe o professor!")
    private ProfessorDTO professor;

    @NotNull(message = "Informe o aluno!")
    private AlunoDTO aluno;

    @NotNull(message = "Informe o curso!")
    private CursoDTO curso;

    private List<MateriaNotaBimestreDTO> notas = new ArrayList<>();

    public BoletimDTO() {}

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

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    public List<MateriaNotaBimestreDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<MateriaNotaBimestreDTO> notas) {
        this.notas = notas;
    }

    public static BoletimDTO of(Boletim boletim) {
        BoletimDTO boletimDTO = new BoletimDTO();
        boletimDTO.setId(boletim.getId());
        boletimDTO.setAno(boletim.getAno());
        boletimDTO.setAluno(AlunoDTO.of(boletim.getAluno()));
        boletimDTO.setProfessor(ProfessorDTO.of(boletim.getProfessor()));
        boletimDTO.setCurso(CursoDTO.of(boletim.getCurso()));

        List<MateriaNotaBimestreDTO> notas = boletim.getNotas().stream().map(MateriaNotaBimestreDTO::of).collect(Collectors.toList());

        boletimDTO.setNotas(notas);
        return boletimDTO;
    }
}
