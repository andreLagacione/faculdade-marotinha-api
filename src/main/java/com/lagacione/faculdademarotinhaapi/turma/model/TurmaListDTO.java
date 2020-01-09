package com.lagacione.faculdademarotinhaapi.turma.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurmaListDTO {

    private Integer id;
    private Integer ano;
    private String curso;
    private String professor;
    private Integer totalAlunos;
    private String periodo;

}
