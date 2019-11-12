package com.lagacione.faculdademarotinhaapi.domain;

import javax.persistence.*;

@Entity
@Table(name = "Aluno")
public class Aluno extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    private Curso curso;

    public Aluno() {}

    public Aluno(String name, Integer age, String cpf, Long phone, Integer id, Curso curso) {
        super(name, age, cpf, phone);
        this.id = id;
        this.curso = curso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
