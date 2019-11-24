package com.lagacione.faculdademarotinhaapi.dto;

import java.util.ArrayList;
import java.util.List;

public class GerarBoletimDTO {
    private Integer ano;
    private String nomeAluno;
    private String nomeCurso;
    private String nomeProfessor;
    private List<ListaNotaGerarBoletimDTO> notas = new ArrayList<>();

    public GerarBoletimDTO() {}

    public GerarBoletimDTO(Integer ano, String nomeAluno, String nomeCurso, String nomeProfessor, List<ListaNotaGerarBoletimDTO> notas) {
        this.ano = ano;
        this.nomeAluno = nomeAluno;
        this.nomeCurso = nomeCurso;
        this.nomeProfessor = nomeProfessor;
        this.notas = notas;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public List<ListaNotaGerarBoletimDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<ListaNotaGerarBoletimDTO> notas) {
        this.notas = notas;
    }
}
