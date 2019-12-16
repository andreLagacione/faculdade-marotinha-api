package com.lagacione.faculdademarotinhaapi.aluno.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.pessoa.entity.Pessoa;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Aluno")
public class Aluno extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
        name = "aluno_curso",
        joinColumns = @JoinColumn(name = "aluno_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "aluno")
    private List<Boletim> boletins = new ArrayList<>();

    public Aluno() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
