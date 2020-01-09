package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.nota.model.NotaPDFDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoletimPDFDTO {

    private Integer ano;
    private String professor;
    private String aluno;
    private String turma;
    private List<NotaPDFDTO> notas = new ArrayList<>();

}
