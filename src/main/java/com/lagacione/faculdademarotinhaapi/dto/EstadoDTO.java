package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Estado;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class EstadoDTO {
    private Integer id;

    @NotEmpty(message = "Campo obrigatório!")
    @Length(min = 3, max = 50, message = "O tamanho deve estar entre 3 e 50 caractéres!")
    private String name;
    private String sigla;

    public EstadoDTO() {}

    public EstadoDTO(Estado estado) {
        this.id = estado.getId();
        this.name = estado.getName();
        this.sigla = estado.getSigla();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla=sigla;
    }
}
