package com.lagacione.faculdademarotinhaapi.turma.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurmaFilter {

    private Integer ano;
    private Integer idCurso;
    private Integer idProfessor;
    private String periodo;

}
