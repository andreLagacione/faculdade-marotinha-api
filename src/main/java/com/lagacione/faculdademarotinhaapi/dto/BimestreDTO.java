package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Bimestre;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BimestreDTO {
    private Integer id;

    @NotNull(message = "Informe o ano.")
    private Integer ano;

    @NotNull(message = "Informe o bimestre.")
    @Min(value = 1, message = "O valor deve estar entre 1 e 4.")
    @Max(value = 4, message = "O valor deve estar entre 1 e 4.")
    private Integer bimestre;

    public BimestreDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getBimestre() {
        return bimestre;
    }

    public void setBimestre(Integer bimestre) {
        this.bimestre = bimestre;
    }

    public static BimestreDTO of(Bimestre bimestre) {
        BimestreDTO bimestreDTO = new BimestreDTO();
        bimestreDTO.setId(bimestre.getId());
        bimestreDTO.setAno(bimestre.getAno());
        bimestreDTO.setBimestre(bimestre.getBimestre());
        return bimestreDTO;
    }
}
