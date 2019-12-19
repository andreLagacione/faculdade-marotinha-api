package com.lagacione.faculdademarotinhaapi.professor.model;

import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe pelo menos uma matéria.")
    private List<Integer> materias = new ArrayList<>();

    @NotNull(message = "Informe pelo menos um curso.")
    private List<Integer> cursos = new ArrayList<>();

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

    public List<Integer> getCursos() {
        return cursos;
    }

    public void setCursos(List<Integer> cursos) {
        this.cursos = cursos;
    }

}
