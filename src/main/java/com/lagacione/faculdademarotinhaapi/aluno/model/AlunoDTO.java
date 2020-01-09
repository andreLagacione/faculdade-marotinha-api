package com.lagacione.faculdademarotinhaapi.aluno.model;

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
public class AlunoDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Informe um curso")
    private List<Integer> turmas = new ArrayList<>();

}
