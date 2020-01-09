package com.lagacione.faculdademarotinhaapi.boletim.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoletimListaDTO {

    private Integer id;
    private Integer ano;
    private String nomeAluno;
    private String nomeProfessor;
    private String nomeTurma;
    private Boolean canPrint;

}
