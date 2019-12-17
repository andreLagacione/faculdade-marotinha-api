package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AlunoDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe um curso")
    private List<Integer> cursos = new ArrayList<>();

    public AlunoDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getCursos() {
        return cursos;
    }

    public void setCursos(List<Integer> cursos) {
        this.cursos = cursos;
    }
}
