package com.lagacione.faculdademarotinhaapi.curso.model;

import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CursoToEditDTO {

    private Integer id;
    private String nome;
    private List<MateriaDTO> materias = new ArrayList<>();

}
