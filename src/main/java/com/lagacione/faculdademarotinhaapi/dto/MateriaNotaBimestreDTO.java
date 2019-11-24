package com.lagacione.faculdademarotinhaapi.dto;

import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.domain.MateriaNotaBimestre;

import javax.validation.constraints.NotNull;

public class MateriaNotaBimestreDTO {
    private Integer id;

    @NotNull(message = "Informe a matéria")
    private Materia materia;

    private Double notaBimestre1;
    private Double notaBimestre2;
    private Double notaBimestre3;
    private Double notaBimestre4;
    private Integer idBoletim;

    public MateriaNotaBimestreDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
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

    public Integer getIdBoletim() {
        return idBoletim;
    }

    public void setIdBoletim(Integer idBoletim) {
        this.idBoletim = idBoletim;
    }

    public static MateriaNotaBimestreDTO of(MateriaNotaBimestre materiaNotaBimestre) {
        MateriaNotaBimestreDTO materiaNotaBimestreDTO = new MateriaNotaBimestreDTO();
        materiaNotaBimestreDTO.setId(materiaNotaBimestre.getId());
        materiaNotaBimestreDTO.setMateria(materiaNotaBimestre.getMateria());
        materiaNotaBimestreDTO.setNotaBimestre1(materiaNotaBimestre.getNotaBimestre1());
        materiaNotaBimestreDTO.setNotaBimestre2(materiaNotaBimestre.getNotaBimestre2());
        materiaNotaBimestreDTO.setNotaBimestre3(materiaNotaBimestre.getNotaBimestre3());
        materiaNotaBimestreDTO.setNotaBimestre4(materiaNotaBimestre.getNotaBimestre4());
        materiaNotaBimestreDTO.setIdBoletim(materiaNotaBimestre.getIdBoletim());
        return materiaNotaBimestreDTO;
    }
}
