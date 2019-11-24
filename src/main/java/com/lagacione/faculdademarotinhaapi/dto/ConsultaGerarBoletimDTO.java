package com.lagacione.faculdademarotinhaapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ConsultaGerarBoletimDTO {

    @NotNull(message = "Informe o ano desejado")
    private Integer ano;

    @NotNull(message = "Selecione o aluno")
    private Integer alunoId;

    @NotNull(message = "Selecione o curso")
    private Integer cursoId;

    public ConsultaGerarBoletimDTO() {}

    public ConsultaGerarBoletimDTO(Integer ano, Integer alunoId, Integer cursoId) {
        this.ano = ano;
        this.alunoId = alunoId;
        this.cursoId = cursoId;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }
}
