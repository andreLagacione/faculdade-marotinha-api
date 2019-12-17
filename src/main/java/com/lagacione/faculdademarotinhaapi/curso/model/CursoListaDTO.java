package com.lagacione.faculdademarotinhaapi.curso.model;

import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;

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

}
