package com.lagacione.faculdademarotinhaapi.domain;

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

    @ManyToOne
    @JoinColumn(name = "id_professor", referencedColumnName = "id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    private Aluno aluno;

    @OneToMany
    @JoinTable(
            name = "boletim_nota",
            joinColumns = @JoinColumn(name = "id_boletim", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_nota", referencedColumnName = "id")
    )
    private List<MateriaNota> materiaNotas = new ArrayList<>();

    public Boletim() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<MateriaNota> getMateriaNotas() {
        return materiaNotas;
    }

    public void setMateriaNotas(List<MateriaNota> materiaNotas) {
        this.materiaNotas = materiaNotas;
    }

    public static Boletim of(BoletimDTO boletimDTO) {
        Boletim boletim = new Boletim();
        boletim.setId(boletimDTO.getId());
        boletim.setProfessor(boletimDTO.getProfessor());
        boletim.setAluno(boletimDTO.getAluno());
        List<MateriaNota> materiaNotas = boletimDTO.getMateriaNotas().stream().map(MateriaNota::of).collect(Collectors.toList());
        boletim.setMateriaNotas(materiaNotas);
        return boletim;
    }
}
