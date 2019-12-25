package com.lagacione.faculdademarotinhaapi.boletim.model;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BoletimDTO {
    private Integer id;

    @NotNull(message = "Informe o ano!")
    private Integer ano;

    @NotNull(message = "Informe o professor!")
    private Integer idProfessor;

    @NotNull(message = "Informe o aluno!")
    private Integer idAluno;

    @NotNull(message = "Informe a turma!")
    private Integer idTurma;

    private List<Integer> notas = new ArrayList<>();

    public BoletimDTO() {}

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

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }

    public List<Integer> getNotas() {
        return notas;
    }

    public void setNotas(List<Integer> notas) {
        this.notas = notas;
    }

}
