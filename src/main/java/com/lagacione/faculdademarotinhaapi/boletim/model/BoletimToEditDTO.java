package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoletimToEditDTO {
    private Integer id;
    private Integer ano;
    private Integer idAluno;
    private Integer idProfessor;
    private Integer idCurso;
    private List<MateriaNotaBimestreListDTO> notas = new ArrayList<>();

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

    public List<MateriaNotaBimestreListDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<MateriaNotaBimestreListDTO> notas) {
        this.notas = notas;
    }

}
