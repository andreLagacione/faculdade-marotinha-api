package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Materia;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class MateriaDTO {
    private Integer id;

    @NotEmpty(message = "Informe o nome!")
    @Length(min = 3, max = 100, message = "O tamanho tem que estar entre 3 e 100 caractéres!")
    private String  name;

    public MateriaDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static MateriaDTO of(Materia materia) {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(materia.getId());
        materiaDTO.setName(materia.getName());
        return materiaDTO;
    }
}
