package com.lagacione.faculdademarotinhaapi.professor.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe pelo menos uma matéria.")
    private List<Integer> materias = new ArrayList<>();

    public ProfessorDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Integer> materias) {
        this.materias = materias;
    }

}
