package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Curso;

public class CursoNomeListaDTO {
    private Integer id;
    private String nome;

    public CursoNomeListaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static CursoNomeListaDTO of(Curso curso) {
        CursoNomeListaDTO cursoNomeListaDTO = new CursoNomeListaDTO();
        cursoNomeListaDTO.setId(curso.getId());
        cursoNomeListaDTO.setNome(curso.getName());
        return cursoNomeListaDTO;
    }
}
