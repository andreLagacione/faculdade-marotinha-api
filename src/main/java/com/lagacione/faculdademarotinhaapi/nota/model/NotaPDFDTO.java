package com.lagacione.faculdademarotinhaapi.nota.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotaPDFDTO {

    private Integer id;
    private String nomeMateria;
    private String notaBimestre1;
    private String notaBimestre2;
    private String notaBimestre3;
    private String notaBimestre4;
    private Integer idBoletim;
    private String mediaFinal = "N/A";

}
