package com.lagacione.faculdademarotinhaapi.materia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class MateriaDTO {

    private Integer id;

    @NotEmpty(message = "Informe o nome!")
    @Length(min = 3, max = 100, message = "O tamanho tem que estar entre 3 e 100 caractéres!")
    private String  name;
    
}
