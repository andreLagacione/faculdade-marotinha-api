package com.lagacione.faculdademarotinhaapi.domain;

import javax.persistence.*;

@Entity
@Table(name = "estado")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sigla", nullable = false)
    private String sigla;

    public Estado() {}

    public Estado(Integer id, String name, String sigla) {
        this.id=id;
        this.name=name;
        this.sigla=sigla;
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
