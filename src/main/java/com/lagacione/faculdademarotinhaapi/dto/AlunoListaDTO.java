package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Aluno;

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
