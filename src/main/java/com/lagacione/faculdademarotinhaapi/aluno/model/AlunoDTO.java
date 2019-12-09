package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlunoDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe um curso")
    private List<CursoDTO> cursos = new ArrayList<>();

    public AlunoDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoDTO> cursos) {
        this.cursos = cursos;
    }

    public static AlunoDTO of(Aluno aluno) {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(aluno.getId());
        alunoDTO.setName(aluno.getName());
        alunoDTO.setAge(aluno.getAge());
        alunoDTO.setCpf(aluno.getCpf());
        alunoDTO.setPhone(aluno.getPhone());
        List<CursoDTO> cursosDTO = aluno.getCursos().stream().map(CursoDTO::of).collect(Collectors.toList());
        alunoDTO.setCursos(cursosDTO);
        return alunoDTO;
    }
}
