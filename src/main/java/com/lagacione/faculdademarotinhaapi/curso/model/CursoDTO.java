package com.lagacione.faculdademarotinhaapi.curso.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CursoDTO {

    private Integer id;

    @NotEmpty(message = "Informe o nome!")
    @Length(min = 3, max = 100, message = "O tamanho deve estar entre 3 e 100 caractéres!")
    private String name;

    @NotNull(message = "Informe pelo menos uma matéria!")
    private List<Integer> materias = new ArrayList<>();

    public CursoDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Integer> materias) {
        this.materias = materias;
    }
}
