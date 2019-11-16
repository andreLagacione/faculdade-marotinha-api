package com.lagacione.faculdademarotinhaapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lagacione.faculdademarotinhaapi.dto.BimestreDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bimestre")
public class Bimestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "bimestre")
    private Integer bimestre;

    @JsonIgnore
    @OneToMany(mappedBy = "bimestre")
    private List<MateriaNota> materiaNotas = new ArrayList<>();

    public Bimestre() {}

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

    public List<MateriaNota> getMateriaNotas() {
        return materiaNotas;
    }

    public void setMateriaNotas(List<MateriaNota> materiaNotas) {
        this.materiaNotas = materiaNotas;
    }

    public static Bimestre of(BimestreDTO bimestreDTO) {
        Bimestre bimestre = new Bimestre();
        bimestre.setId(bimestreDTO.getId());
        bimestre.setAno(bimestreDTO.getAno());
        bimestre.setBimestre(bimestreDTO.getBimestre());
        return bimestre;
    }
}
