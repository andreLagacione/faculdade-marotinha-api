package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;

public class BoletimFilter {

    private Integer ano;
    private Integer idProfessor;
    private Aluno aluno;
    private Turma turma;

    public Integer getAno() {
        return ano;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Turma getTurma() {
        return turma;
    }
}
