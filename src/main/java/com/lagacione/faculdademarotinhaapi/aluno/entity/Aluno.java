package com.lagacione.faculdademarotinhaapi.aluno.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.pessoa.entity.Pessoa;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Aluno")
public class Aluno extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "aluno")
    private List<Boletim> boletins = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "aluno_turma",
            joinColumns = @JoinColumn(name = "id_aluno"),
            inverseJoinColumns = @JoinColumn(name = "id_turma")
    )
    private List<Turma> turmas = new ArrayList<>();

}
