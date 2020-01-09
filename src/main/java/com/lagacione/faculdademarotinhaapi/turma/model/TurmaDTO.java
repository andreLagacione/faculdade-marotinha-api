package com.lagacione.faculdademarotinhaapi.turma.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TurmaDTO {

    private Integer id;

    @NotNull(message = "Informe o ano!")
    private Integer ano;

    @NotNull(message = "Informe o curso!")
    private Integer curso;

    @NotNull(message = "Informe o professor!")
    private Integer professor;

    @NotEmpty(message = "Informe o periodo!")
    private String periodo;

}
