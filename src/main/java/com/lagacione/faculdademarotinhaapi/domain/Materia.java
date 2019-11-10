package com.lagacione.faculdademarotinhaapi.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materia")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String  name;

    @ManyToMany(mappedBy = "materias")
    private List<Curso> cursos = new ArrayList<>();

    @ManyToMany(mappedBy = "materiasLecionadas")
    private List<Professor> professores;

    public Materia() {}

    public Materia(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
