package com.lagacione.faculdademarotinhaapi.professor.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;

public class ProfessorListaDTO extends PessoaDTO {
    private Integer id;

    public ProfessorListaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public static ProfessorListaDTO of(Professor professor) {
        ProfessorListaDTO professorListaDTO = new ProfessorListaDTO();
        professorListaDTO.setId(professor.getId());
        professorListaDTO.setName(professor.getName());
        professorListaDTO.setAge(professor.getAge());
        professorListaDTO.setCpf(professor.getCpf());
        professorListaDTO.setPhone(professor.getPhone());
        return professorListaDTO;
    }
}
