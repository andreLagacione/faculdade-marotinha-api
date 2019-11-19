package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;

public class ListaNotaBoletimDTO {
    private String professor;
    private String aluno;
    private String bimestre;
    private String materia;
    private Double nota;

    public ListaNotaBoletimDTO() {}

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getBimestre() {
        return bimestre;
    }

    public void setBimestre(String bimestre) {
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

    public static ListaNotaBoletimDTO of(MateriaNota materiaNota) {
        ListaNotaBoletimDTO boletim = new ListaNotaBoletimDTO();
        boletim.setAluno(materiaNota.getAluno().getName());
        boletim.setProfessor(materiaNota.getProfessor().getName());
        boletim.setMateria(materiaNota.getMateria().getName());
        boletim.setNota(materiaNota.getNota());

        String bimestre = Integer.toString(materiaNota.getBimestre().getBimestre());
        String ano = Integer.toString(materiaNota.getBimestre().getAno());

        boletim.setBimestre(bimestre + "/" + ano);
        return boletim;
    }
}
