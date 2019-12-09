package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
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

    public static AlunoListaDTO of(Aluno aluno) {
        AlunoListaDTO alunoListaDTO = new AlunoListaDTO();
        alunoListaDTO.setId(aluno.getId());
        alunoListaDTO.setName(aluno.getName());
        alunoListaDTO.setCpf(aluno.getCpf());
        alunoListaDTO.setAge(aluno.getAge());
        alunoListaDTO.setPhone(aluno.getPhone());
        return alunoListaDTO;
    }
}
