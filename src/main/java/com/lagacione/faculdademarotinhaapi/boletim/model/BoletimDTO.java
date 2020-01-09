package com.lagacione.faculdademarotinhaapi.boletim.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoletimDTO {
    private Integer id;

    @NotNull(message = "Informe o ano!")
    private Integer ano;

    @NotNull(message = "Informe o professor!")
    private Integer idProfessor;

    @NotNull(message = "Informe o aluno!")
    private Integer idAluno;

    @NotNull(message = "Informe a turma!")
    private Integer idTurma;

    private List<Integer> notas = new ArrayList<>();

}
