package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.curso.model.CursoListaDTO;
import com.lagacione.faculdademarotinhaapi.domain.Professor;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorToEditDTO extends PessoaDTO {
    private Integer id;
    private List<MateriaDTO> materias = new ArrayList<>();
    private List<CursoListaDTO> cursos = new ArrayList<>();

    public ProfessorToEditDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public List<MateriaDTO> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaDTO> listaMaterias) {
        this.materias=listaMaterias;
    }

    public List<CursoListaDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoListaDTO> listaCursos) {
        this.cursos=listaCursos;
    }

    public static ProfessorToEditDTO of(Professor professor) {
        ProfessorToEditDTO professorToEditDTO = new ProfessorToEditDTO();
        professorToEditDTO.setId(professor.getId());
        professorToEditDTO.setName(professor.getName());
        professorToEditDTO.setAge(professor.getAge());
        professorToEditDTO.setCpf(professor.getCpf());
        professorToEditDTO.setPhone(professor.getPhone());

        List<MateriaDTO> materias = professor.getMateriasLecionadas().stream().map(MateriaDTO::of).collect(Collectors.toList());
        List<CursoListaDTO> cursos = professor.getCursosLecionados().stream().map(CursoListaDTO::of).collect(Collectors.toList());

        professorToEditDTO.setMaterias(materias);
        professorToEditDTO.setCursos(cursos);
        return professorToEditDTO;
    }
}
