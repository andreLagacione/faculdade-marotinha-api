package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.nota.model.NotaListDTO;

import java.util.ArrayList;
import java.util.List;

public class BoletimToEditDTO {
    private Integer id;
    private Integer ano;
    private Integer idAluno;
    private Integer idProfessor;
    private Integer idCurso;
    private List<NotaListDTO> notas = new ArrayList<>();

    public BoletimToEditDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public List<NotaListDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaListDTO> notas) {
        this.notas = notas;
    }

}
