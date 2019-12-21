package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaListDTO;

import java.util.ArrayList;
import java.util.List;

public class AlunoForEditDTO extends PessoaDTO {
    private Integer id;
    private List<TurmaListDTO> turmas = new ArrayList<>();

    public AlunoForEditDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TurmaListDTO> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<TurmaListDTO> turmas) {
        this.turmas = turmas;
    }
}
