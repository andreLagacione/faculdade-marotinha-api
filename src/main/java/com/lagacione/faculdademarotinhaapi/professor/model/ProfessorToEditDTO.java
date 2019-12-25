package com.lagacione.faculdademarotinhaapi.professor.model;

import com.lagacione.faculdademarotinhaapi.curso.model.CursoListaDTO;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaComboListDTO;

import java.util.ArrayList;
import java.util.List;

public class ProfessorToEditDTO extends PessoaDTO {
    private Integer id;
    private List<MateriaDTO> materias = new ArrayList<>();

    public ProfessorToEditDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MateriaDTO> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaDTO> listaMaterias) {
        this.materias = listaMaterias;
    }

}
