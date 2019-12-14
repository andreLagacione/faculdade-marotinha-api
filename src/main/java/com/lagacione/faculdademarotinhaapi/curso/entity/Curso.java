package com.lagacione.faculdademarotinhaapi.curso.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;

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
    private List<Boletim> boletins = new ArrayList<>();

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

    public static Curso of(CursoDTO cursoDTO, List<Materia> materias) {
        Curso curso = new Curso();
        curso.setId(cursoDTO.getId());
        curso.setMaterias(materias);
        curso.setName(cursoDTO.getName());
        return curso;
    }
}
