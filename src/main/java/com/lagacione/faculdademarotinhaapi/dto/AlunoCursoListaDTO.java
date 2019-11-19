package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlunoCursoListaDTO extends PessoaDTO {
    private Integer id;
    private List<CursoNomeListaDTO> cursoNomeListaDTOList = new ArrayList<>();

    public AlunoCursoListaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CursoNomeListaDTO> getCursoNomeListaDTOList() {
        return cursoNomeListaDTOList;
    }

    public void setCursoNomeListaDTOList(List<CursoNomeListaDTO> cursoNomeListaDTOList) {
        this.cursoNomeListaDTOList = cursoNomeListaDTOList;
    }

    public static AlunoCursoListaDTO of (Aluno aluno) {
        AlunoCursoListaDTO alunoCursoListaDTO = new AlunoCursoListaDTO();
        alunoCursoListaDTO.setId(aluno.getId());
        alunoCursoListaDTO.setName(aluno.getName());
        alunoCursoListaDTO.setCpf(aluno.getCpf());
        alunoCursoListaDTO.setAge(aluno.getAge());
        alunoCursoListaDTO.setPhone(aluno.getPhone());
        List<CursoNomeListaDTO> cursos = aluno.getCursos().stream().map(CursoNomeListaDTO::of).collect(Collectors.toList());
        alunoCursoListaDTO.setCursoNomeListaDTOList(cursos);
        return alunoCursoListaDTO;
    }
}
