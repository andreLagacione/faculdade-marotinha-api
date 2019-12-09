package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.domain.Professor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe pelo menos uma matéria.")
    private List<MateriaDTO> materiasLecionadas = new ArrayList<>();

    @NotNull(message = "Informe pelo menos um curso.")
    private List<CursoDTO> cursosLecionados = new ArrayList<>();

    public ProfessorDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MateriaDTO> getMateriasLecionadas() {
        return materiasLecionadas;
    }

    public void setMateriasLecionadas(List<MateriaDTO> materiasLecionadas) {
        this.materiasLecionadas = materiasLecionadas;
    }

    public List<CursoDTO> getCursosLecionados() {
        return cursosLecionados;
    }

    public void setCursosLecionados(List<CursoDTO> cursosLecionados) {
        this.cursosLecionados = cursosLecionados;
    }

    public static ProfessorDTO of(Professor professor) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professor.getId());
        professorDTO.setName(professor.getName());
        professorDTO.setAge(professor.getAge());
        professorDTO.setCpf(professor.getCpf());
        professorDTO.setPhone(professor.getPhone());
        List<MateriaDTO> materiasDTO = professor.getMateriasLecionadas().stream().map(MateriaDTO::of).collect(Collectors.toList());
        List<CursoDTO> cursosDTO = professor.getCursosLecionados().stream().map(CursoDTO::of).collect(Collectors.toList());
        professorDTO.setMateriasLecionadas(materiasDTO);
        professorDTO.setCursosLecionados(cursosDTO);
        return professorDTO;
    }
}
