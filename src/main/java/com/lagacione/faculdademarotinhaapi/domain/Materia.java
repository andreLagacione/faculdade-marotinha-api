package com.lagacione.faculdademarotinhaapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.dto.MateriaDTO;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "materias")
    private List<Curso> cursos = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "materiasLecionadas")
    private List<Professor> professores;

    @JsonIgnore
    @OneToMany(mappedBy = "materia")
    private List<MateriaNotaBimestre> materiaNotasBimestre = new ArrayList<>();

    public Materia() {}

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

    public static Materia of(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setId(materiaDTO.getId());
        materia.setName(materiaDTO.getName());
        return materia;
    }
}
