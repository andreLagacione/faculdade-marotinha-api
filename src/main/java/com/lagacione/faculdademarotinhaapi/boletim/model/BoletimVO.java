package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.nota.entity.Nota;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;

import java.util.ArrayList;
import java.util.List;

public class BoletimVO {
    private Integer id;
    private Integer ano;
    private Professor professor;
    private Aluno aluno;
    private Turma turma;
    private List<Nota> notas = new ArrayList<>();

    public BoletimVO(Object[] line) {
        Boletim boletim = new Boletim();
        boletim.setId((Integer) line[0]);
        boletim.setAno((Integer) line[1]);
        boletim.setProfessor((Professor) line[2]);
        boletim.setAluno((Aluno) line[3]);
        boletim.setTurma((Turma) line[4]);
        boletim.setNotas((List<Nota>) line[5]);
    }

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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
}
