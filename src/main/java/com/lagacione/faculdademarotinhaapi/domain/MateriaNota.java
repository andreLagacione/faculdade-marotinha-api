package com.lagacione.faculdademarotinhaapi.domain;

import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaDTO;

import javax.persistence.*;

@Entity
@Table(name = "nota")
public class MateriaNota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "id_bimestre", referencedColumnName = "id")
    private Bimestre bimestre;

    @ManyToOne
    @JoinColumn(name = "id_materia", referencedColumnName = "id")
    private Materia materia;

    @Column(name = "nota")
    private Double nota;

    public MateriaNota() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Bimestre getBimestre() {
        return bimestre;
    }

    public void setBimestre(Bimestre bimestre) {
        this.bimestre = bimestre;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public static MateriaNota of(MateriaNotaDTO materiaNotaDTO) {
        MateriaNota materiaNota = new MateriaNota();
        materiaNota.setId(materiaNotaDTO.getId());
        materiaNota.setAluno(materiaNotaDTO.getAluno());
        materiaNota.setCurso(materiaNotaDTO.getCurso());
        materiaNota.setBimestre(Bimestre.of(materiaNotaDTO.getBimestre()));
        materiaNota.setMateria(materiaNotaDTO.getMateria());
        materiaNota.setNota(materiaNotaDTO.getNota());
        return materiaNota;
    }
}
