package com.lagacione.faculdademarotinhaapi.turma.model;

import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurmaEditDTO {

    private Integer id;
    private Integer ano;
    private Curso curso;
    private Professor professor;
    private String periodo;

}
