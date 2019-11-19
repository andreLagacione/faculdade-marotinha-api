package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;

public class ListaNotaIntoBoletimDTO {
    private String materia;
    private Double nota;

    public ListaNotaIntoBoletimDTO() {}

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public static ListaNotaIntoBoletimDTO of(MateriaNota materiaNota) {
        ListaNotaIntoBoletimDTO boletim = new ListaNotaIntoBoletimDTO();
        boletim.setMateria(materiaNota.getMateria().getName());
        boletim.setNota(materiaNota.getNota());
        return boletim;
    }
}
