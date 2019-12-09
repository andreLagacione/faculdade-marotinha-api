package com.lagacione.faculdademarotinhaapi.curso.model;

import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.dto.MateriaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoToEditDTO {
    private Integer id;
    private String nome;
    private List<MateriaDTO> materias = new ArrayList<>();

    public CursoToEditDTO() {}

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

    public List<MateriaDTO> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaDTO> idMaterias) {
        this.materias=idMaterias;
    }

    public static CursoToEditDTO of(Curso curso) {
        CursoToEditDTO cursoToEditDTO = new CursoToEditDTO();
        cursoToEditDTO.setId(curso.getId());
        cursoToEditDTO.setNome(curso.getName());
        List<MateriaDTO> materias = curso.getMaterias().stream().map(MateriaDTO::of).collect(Collectors.toList());
        cursoToEditDTO.setMaterias(materias);
        return cursoToEditDTO;
    }
}
