package com.lagacione.faculdademarotinhaapi.nota.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class NotaDTO {

    private Integer id;

    @NotNull(message = "Informe a matéria")
    private Integer idMateria;

    private Double notaBimestre1;
    private Double notaBimestre2;
    private Double notaBimestre3;
    private Double notaBimestre4;
    private Integer idBoletim;
    private String mediaFinal = "N/A";

}
