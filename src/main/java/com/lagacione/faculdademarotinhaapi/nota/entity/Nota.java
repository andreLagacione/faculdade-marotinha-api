package com.lagacione.faculdademarotinhaapi.nota.entity;

import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "nota")
public class Nota {

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

    @Column(name = "media_final")
    private String mediaFinal = "N/A";

}
