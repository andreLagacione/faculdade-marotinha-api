package com.lagacione.faculdademarotinhaapi.aluno.model;

import com.lagacione.faculdademarotinhaapi.pessoa.model.PessoaDTO;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaComboListDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AlunoForEditDTO extends PessoaDTO {

    private Integer id;
    private List<TurmaComboListDTO> turmas = new ArrayList<>();

}
