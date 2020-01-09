package com.lagacione.faculdademarotinhaapi.boletim.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoletimFilter {

    private Integer ano;
    private Integer idProfessor;
    private Integer idAluno;
    private Integer idTurma;

}
