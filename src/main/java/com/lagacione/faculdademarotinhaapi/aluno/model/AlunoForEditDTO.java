package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.curso.model.CursoNomeListaDTO;
import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;

import java.util.ArrayList;
import java.util.List;

public class AlunoForEditDTO extends PessoaDTO {
    private Integer id;
    private List<CursoNomeListaDTO> cursos = new ArrayList<>();

    public AlunoForEditDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CursoNomeListaDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoNomeListaDTO> cursoNomeListaDTOList) {
        this.cursos = cursoNomeListaDTOList;
    }
}
