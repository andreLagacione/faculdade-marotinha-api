package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MateriaNotaDTO {
    private Integer id;

    @NotNull(message = "Informe o aluno.")
    private Aluno aluno;

    @NotNull(message = "Informe o curso.")
    private Curso curso;

    @NotNull(message = "Informe o bimestre.")
    private BimestreDTO bimestre;

    @NotNull(message = "Informe o materia.")
    private Materia materia;

    @NotNull(message = "Informe o nota.")
    @Min(value = 0, message = "A nota deve estar entre 0 e 10.")
    @Max(value = 10, message = "A nota deve estar entre 0 e 10.")
    private Double nota;

    public MateriaNotaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public BimestreDTO getBimestre() {
        return bimestre;
    }

    public void setBimestre(BimestreDTO bimestre) {
        this.bimestre = bimestre;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public static MateriaNotaDTO of(MateriaNota materiaNota) {
        MateriaNotaDTO materiaNotaDTO = new MateriaNotaDTO();
        materiaNotaDTO.setId(materiaNota.getId());
        materiaNotaDTO.setAluno(materiaNota.getAluno());
        materiaNotaDTO.setCurso(materiaNota.getCurso());
        materiaNotaDTO.setBimestre(BimestreDTO.of(materiaNota.getBimestre()));
        materiaNotaDTO.setMateria(materiaNota.getMateria());
        materiaNotaDTO.setNota(materiaNota.getNota());
        return materiaNotaDTO;
    }
}
