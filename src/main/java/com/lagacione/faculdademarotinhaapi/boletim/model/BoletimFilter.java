package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;

public class BoletimFilter {

    private Integer ano;
    private Professor professor;
    private Aluno aluno;
    private Turma turma;

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
}
