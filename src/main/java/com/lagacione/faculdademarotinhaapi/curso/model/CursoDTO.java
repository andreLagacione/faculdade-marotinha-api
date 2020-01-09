package com.lagacione.faculdademarotinhaapi.curso.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CursoDTO {

    private Integer id;

    @NotEmpty(message = "Informe o nome!")
    @Length(min = 3, max = 100, message = "O tamanho deve estar entre 3 e 100 caractéres!")
    private String name;

    @NotNull(message = "Informe pelo menos uma matéria!")
    private List<Integer> materias = new ArrayList<>();

}
