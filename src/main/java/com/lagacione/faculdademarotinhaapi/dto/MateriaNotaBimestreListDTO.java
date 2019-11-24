package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNotaBimestre;

public class MateriaNotaBimestreListDTO {
    private Integer id;
    private String nomeMateria;
    private Double notaBimestre1;
    private Double notaBimestre2;
    private Double notaBimestre3;
    private Double notaBimestre4;

    public MateriaNotaBimestreListDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public Double getNotaBimestre1() {
        return notaBimestre1;
    }

    public void setNotaBimestre1(Double notaBimestre1) {
        this.notaBimestre1 = notaBimestre1;
    }

    public Double getNotaBimestre2() {
        return notaBimestre2;
    }

    public void setNotaBimestre2(Double notaBimestre2) {
        this.notaBimestre2 = notaBimestre2;
    }

    public Double getNotaBimestre3() {
        return notaBimestre3;
    }

    public void setNotaBimestre3(Double notaBimestre3) {
        this.notaBimestre3 = notaBimestre3;
    }

    public Double getNotaBimestre4() {
        return notaBimestre4;
    }

    public void setNotaBimestre4(Double notaBimestre4) {
        this.notaBimestre4 = notaBimestre4;
    }

    public static MateriaNotaBimestreListDTO of(MateriaNotaBimestre materiaNotaBimestre) {
        MateriaNotaBimestreListDTO materiaNotaBimestreListDTO = new MateriaNotaBimestreListDTO();
        materiaNotaBimestreListDTO.setId(materiaNotaBimestre.getId());
        materiaNotaBimestreListDTO.setNomeMateria(materiaNotaBimestre.getMateria().getName());
        materiaNotaBimestreListDTO.setNotaBimestre1(materiaNotaBimestre.getNotaBimestre1());
        materiaNotaBimestreListDTO.setNotaBimestre2(materiaNotaBimestre.getNotaBimestre2());
        materiaNotaBimestreListDTO.setNotaBimestre3(materiaNotaBimestre.getNotaBimestre3());
        materiaNotaBimestreListDTO.setNotaBimestre4(materiaNotaBimestre.getNotaBimestre4());
        return materiaNotaBimestreListDTO;
    }
}
