package com.lagacione.faculdademarotinhaapi.materia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.nota.entity.Nota;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
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
    private List<Nota> materiaNotasBimestre = new ArrayList<>();

}
