package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Curso;
import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.domain.Pessoa;
import com.lagacione.faculdademarotinhaapi.domain.Professor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDTO extends PessoaDTO {

    private Integer id;

    @NotNull(message = "Campo obrigatório! Selecione ao menos uma matéria.")
    private List<Materia> materiasLecionadas = new ArrayList<>();

    @NotNull(message = "Campo obrigatório! Selecione ao menos um curso.")
    private List<Curso> cursosLecionados = new ArrayList<>();

    public ProfessorDTO() {}

    public ProfessorDTO(String name, Integer age, String cpf, Long phone, Integer id, List<Materia> materiasLecionadas, List<Curso> cursosLecionados) {
        super(name, age, cpf, phone);
        this.id = id;
        this.materiasLecionadas = materiasLecionadas;
        this.cursosLecionados = cursosLecionados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Materia> getMateriasLecionadas() {
        return materiasLecionadas;
    }

    public void setMateriasLecionadas(List<Materia> materiasLecionadas) {
        this.materiasLecionadas = materiasLecionadas;
    }

    public List<Curso> getCursosLecionados() {
        return cursosLecionados;
    }

    public void setCursosLecionados(List<Curso> cursosLecionados) {
        this.cursosLecionados = cursosLecionados;
    }
}
