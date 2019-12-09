package com.lagacione.faculdademarotinhaapi.domain;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.dto.BoletimDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "boletim")
public class Boletim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_professor", referencedColumnName = "id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @ManyToMany
    @JoinTable(
        name = "materias_boletim",
        joinColumns = @JoinColumn(name = "id_boletim", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "id_nota", referencedColumnName = "id")
    )
    private List<MateriaNotaBimestre> notas = new ArrayList<>();

    public Boletim() {}

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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<MateriaNotaBimestre> getNotas() {
        return notas;
    }

    public void setNotas(List<MateriaNotaBimestre> notas) {
        this.notas = notas;
    }

    public  static Boletim of(BoletimDTO boletimDTO) {
        Boletim boletim = new Boletim();
        boletim.setId(boletimDTO.getId());
        boletim.setAno(boletimDTO.getAno());
        boletim.setAluno(Aluno.of(boletimDTO.getAluno()));
        boletim.setProfessor(Professor.of(boletimDTO.getProfessor()));
        boletim.setCurso(Curso.of(boletimDTO.getCurso()));

        List<MateriaNotaBimestre> notas = boletimDTO.getNotas().stream().map(MateriaNotaBimestre::of).collect(Collectors.toList());

        boletim.setNotas(notas);
        return boletim;
    }
}
