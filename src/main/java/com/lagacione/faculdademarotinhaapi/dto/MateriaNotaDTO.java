package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;

public class MateriaNotaDTO {
    private Integer id;
    private Materia materia;
    private Double nota;

    public MateriaNotaDTO() {}

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

    public static MateriaNotaDTO of(MateriaNota materiaNota) {
        MateriaNotaDTO materiaNotaDTO = new MateriaNotaDTO();
        materiaNotaDTO.setId(materiaNota.getId());
        materiaNotaDTO.setMateria(materiaNota.getMateria());
        materiaNotaDTO.setNota(materiaNota.getNota());
        return materiaNotaDTO;
    }
}
