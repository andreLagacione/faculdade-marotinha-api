package com.lagacione.faculdademarotinhaapi.dto;

public class ListaNotaBoletimDTO {
    private BimestreDTO bimestre;
    private String materia;
    private Double nota;

    public ListaNotaBoletimDTO() {}

    public BimestreDTO getBimestre() {
        return bimestre;
    }

    public void setBimestre(BimestreDTO bimestre) {
        this.bimestre = bimestre;
    }

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

    public static ListaNotaBoletimDTO of(MateriaNotaDTO materiaNota) {
        ListaNotaBoletimDTO boletim = new ListaNotaBoletimDTO();
        boletim.setBimestre(materiaNota.getBimestre());
        boletim.setMateria(materiaNota.getMateria().getName());
        boletim.setNota(materiaNota.getNota());
        return boletim;
    }
}
