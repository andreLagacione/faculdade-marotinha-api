package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorToEditDTO extends PessoaDTO {
    private Integer id;
    private List<Integer> listaMaterias = new ArrayList<>();
    private List<Integer> listaCursos = new ArrayList<>();

    public ProfessorToEditDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public List<Integer> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<Integer> listaMaterias) {
        this.listaMaterias=listaMaterias;
    }

    public List<Integer> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(List<Integer> listaCursos) {
        this.listaCursos=listaCursos;
    }

    public static ProfessorToEditDTO of(Professor professor) {
        ProfessorToEditDTO professorToEditDTO = new ProfessorToEditDTO();
        professorToEditDTO.setId(professor.getId());
        professorToEditDTO.setName(professor.getName());
        professorToEditDTO.setAge(professor.getAge());
        professorToEditDTO.setCpf(professor.getCpf());
        professorToEditDTO.setPhone(professor.getPhone());

        List<Integer> materias = professor.getMateriasLecionadas().stream().map(materia -> materia.getId()).collect(Collectors.toList());
        List<Integer> cursos = professor.getCursosLecionados().stream().map(curso -> curso.getId()).collect(Collectors.toList());

        professorToEditDTO.setListaMaterias(materias);
        professorToEditDTO.setListaCursos(cursos);
        return professorToEditDTO;
    }
}
