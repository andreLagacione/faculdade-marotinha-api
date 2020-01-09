package com.lagacione.faculdademarotinhaapi.professor.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProfessorDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe pelo menos uma matéria.")
    private List<Integer> materias = new ArrayList<>();

}
