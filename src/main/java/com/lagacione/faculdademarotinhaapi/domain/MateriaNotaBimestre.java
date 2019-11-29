package com.lagacione.faculdademarotinhaapi.domain;

import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaBimestreDTO;

import javax.persistence.*;

@Entity
@Table(name = "materia_nota_bimestre")
public class MateriaNotaBimestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "materia_id", referencedColumnName = "id")
    private Materia materia;

    @Column(name = "nota_bimestre_1")
    private Double notaBimestre1;

    @Column(name = "nota_bimestre_2")
    private Double notaBimestre2;

    @Column(name = "nota_bimestre_3")
    private Double notaBimestre3;

    @Column(name = "nota_bimestre_4")
    private Double notaBimestre4;

    @Column(name = "boletim_id")
    private Integer idBoletim;

    public MateriaNotaBimestre() {}

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

    public static MateriaNotaBimestre of(MateriaNotaBimestreDTO materiaNotaBimestreDTO) {
        MateriaNotaBimestre materiaNotaBimestre = new MateriaNotaBimestre();
        materiaNotaBimestre.setId(materiaNotaBimestreDTO.getId());
        materiaNotaBimestre.setMateria(Materia.of(materiaNotaBimestreDTO.getMateria()));
        materiaNotaBimestre.setNotaBimestre1(materiaNotaBimestreDTO.getNotaBimestre1());
        materiaNotaBimestre.setNotaBimestre2(materiaNotaBimestreDTO.getNotaBimestre2());
        materiaNotaBimestre.setNotaBimestre3(materiaNotaBimestreDTO.getNotaBimestre3());
        materiaNotaBimestre.setNotaBimestre4(materiaNotaBimestreDTO.getNotaBimestre4());
        materiaNotaBimestre.setIdBoletim(materiaNotaBimestreDTO.getIdBoletim());
        return materiaNotaBimestre;
    }
}
