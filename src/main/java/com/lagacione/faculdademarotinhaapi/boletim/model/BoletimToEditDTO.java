package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.nota.model.NotaListDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoletimToEditDTO {

    private Integer id;
    private Integer ano;
    private Integer idAluno;
    private Integer idProfessor;
    private Integer idTurma;
    private List<NotaListDTO> notas = new ArrayList<>();

}
