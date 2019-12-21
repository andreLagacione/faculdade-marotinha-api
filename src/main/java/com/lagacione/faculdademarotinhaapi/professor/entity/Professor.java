package com.lagacione.faculdademarotinhaapi.professor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.pessoa.entity.Pessoa;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professor")
public class Professor extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "professor_materias",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private List<Materia> materiasLecionadas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "professor")
    private List<Boletim> boletins = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "professor")
    private List<Turma> turmas = new ArrayList<>();

    public Professor() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Materia> getMateriasLecionadas() {
        return materiasLecionadas;
    }

    public void setMateriasLecionadas(List<Materia> materiasLecionadas) {
        this.materiasLecionadas = materiasLecionadas;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }
}
