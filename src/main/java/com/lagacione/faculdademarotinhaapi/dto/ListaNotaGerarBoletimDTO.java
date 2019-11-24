package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;

public class ListaNotaGerarBoletimDTO {
    private String nomeMateria;
    private Integer bimestre;
    private Double nota;

    public ListaNotaGerarBoletimDTO() {}

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public Integer getBimestre() {
        return bimestre;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public static ListaNotaGerarBoletimDTO of(MateriaNota materiaNota) {
        ListaNotaGerarBoletimDTO notas = new ListaNotaGerarBoletimDTO();
        notas.setNomeMateria(materiaNota.getMateria().getName());
        notas.setBimestre(materiaNota.getBimestre().getBimestre());
        notas.setNota(materiaNota.getNota());
        return notas;
    }
}
