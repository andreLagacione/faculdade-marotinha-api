package com.lagacione.faculdademarotinhaapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.dto.CursoDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "curso_materias",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private List<Materia> materias = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "cursosLecionados")
    private List<Professor> professores = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "cursos")
    private List<Aluno> alunos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "curso")
    private List<MateriaNota> materiaNotas = new ArrayList<>();

    public Curso() {}

    public Curso(Integer id, String name, List<Materia> materias) {
        this.id = id;
        this.name = name;
        this.materias = materias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<MateriaNota> getMateriaNotas() {
        return materiaNotas;
    }

    public void setMateriaNotas(List<MateriaNota> materiaNotas) {
        this.materiaNotas = materiaNotas;
    }

    public static Curso of(CursoDTO cursoDTO) {
        Curso curso = new Curso();
        curso.setId(cursoDTO.getId());
        List<Materia> materias = cursoDTO.getMaterias().stream().map(Materia::of).collect(Collectors.toList());
        curso.setMaterias(materias);
        curso.setName(cursoDTO.getName());
        return curso;
    }
}
