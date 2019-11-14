package com.lagacione.faculdademarotinhaapi.domain;

import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MateriaNota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Materia materia;
    private Double nota;

    public MateriaNota() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        materiaNota.setId(materiaNota.getId());
        materiaNota.setMateria(materiaNotaDTO.getMateria());
        materiaNota.setNota(materiaNotaDTO.getNota());
        return materiaNota;
    }
}
