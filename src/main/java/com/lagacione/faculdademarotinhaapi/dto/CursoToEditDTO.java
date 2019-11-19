package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Curso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoToEditDTO {
    private Integer id;
    private String nome;
    private List<Integer> idMaterias = new ArrayList<>();

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

    public List<Integer> getIdMaterias() {
        return idMaterias;
    }

    public void setIdMaterias(List<Integer> idMaterias) {
        this.idMaterias=idMaterias;
    }

    public static CursoToEditDTO of(Curso curso) {
        CursoToEditDTO cursoToEditDTO = new CursoToEditDTO();
        cursoToEditDTO.setId(curso.getId());
        cursoToEditDTO.setNome(curso.getName());
        List<Integer> materias = curso.getMaterias().stream().map(materia -> materia.getId()).collect(Collectors.toList());
        cursoToEditDTO.setIdMaterias(materias);
        return cursoToEditDTO;
    }
}
