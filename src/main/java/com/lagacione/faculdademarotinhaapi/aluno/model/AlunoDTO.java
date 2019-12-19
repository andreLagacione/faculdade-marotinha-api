package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AlunoDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe um curso")
    private List<Integer> turmas = new ArrayList<>();

    public AlunoDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Integer> turmas) {
        this.turmas = turmas;
    }
}
