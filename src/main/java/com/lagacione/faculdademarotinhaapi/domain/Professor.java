package com.lagacione.faculdademarotinhaapi.domain;

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

    @ManyToMany
    @JoinTable(
        name = "professor_cursos",
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursosLecionados = new ArrayList<>();

    public Professor() {}

    public Professor(String name, Integer age, String cpf, Long phone, Integer id, List<Materia> materiasLecionadas, List<Curso> cursosLecionados) {
        super(name, age, cpf, phone);
        this.id = id;
        this.materiasLecionadas = materiasLecionadas;
        this.cursosLecionados = cursosLecionados;
    }

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

    public List<Curso> getCursosLecionados() {
        return cursosLecionados;
    }

    public void setCursosLecionados(List<Curso> cursosLecionados) {
        this.cursosLecionados = cursosLecionados;
    }
}
