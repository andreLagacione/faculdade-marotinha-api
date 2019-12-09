package com.lagacione.faculdademarotinhaapi.curso.model;

import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoDTO {

    private Integer id;

    @NotEmpty(message = "Informe o nome!")
    @Length(min = 3, max = 100, message = "O tamanho deve estar entre 3 e 100 caractéres!")
    private String name;

    @NotNull(message = "Informe pelo menos uma matéria!")
    private List<MateriaDTO> materias = new ArrayList<>();

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

    public List<MateriaDTO> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaDTO> materias) {
        this.materias = materias;
    }

    public static CursoDTO of(Curso curso) {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        List<MateriaDTO> materiasDTO = curso.getMaterias().stream().map(MateriaDTO::of).collect(Collectors.toList());
        cursoDTO.setMaterias(materiasDTO);
        cursoDTO.setName(curso.getName());
        return cursoDTO;
    }
}
