package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Curso;

public class CursoListaDTO {
    private Integer id;
    private String nome;

    public CursoListaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome=nome;
    }

    public static CursoListaDTO of(Curso curso) {
        CursoListaDTO cursoListaDTO = new CursoListaDTO();
        cursoListaDTO.setId(curso.getId());
        cursoListaDTO.setNome(curso.getName());
        return cursoListaDTO;
    }
}
