package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MateriaNotaDTO {
    private Integer id;

    @NotNull(message = "Informe o aluno.")
    private AlunoDTO aluno;

    @NotNull(message = "Informe o professor.")
    private ProfessorDTO professor;

    @NotNull(message = "Informe o curso.")
    private Curso curso;

    @NotNull(message = "Informe o bimestre.")
    private BimestreDTO bimestre;

    @NotNull(message = "Informe o materia.")
    private MateriaDTO materia;

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

    public AlunoDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }

    public ProfessorDTO getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDTO professor) {
        this.professor = professor;
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

    public MateriaDTO getMateria() {
        return materia;
    }

    public void setMateria(MateriaDTO materia) {
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
        materiaNotaDTO.setAluno(AlunoDTO.of(materiaNota.getAluno()));
        materiaNotaDTO.setProfessor(ProfessorDTO.of(materiaNota.getProfessor()));
        materiaNotaDTO.setCurso(materiaNota.getCurso());
        materiaNotaDTO.setBimestre(BimestreDTO.of(materiaNota.getBimestre()));
        materiaNotaDTO.setMateria(MateriaDTO.of(materiaNota.getMateria()));
        materiaNotaDTO.setNota(materiaNota.getNota());
        return materiaNotaDTO;
    }
}
