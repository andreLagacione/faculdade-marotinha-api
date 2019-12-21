package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;

public class AlunoListaDTO extends PessoaDTO {
    private Integer id;

    public AlunoListaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

}
