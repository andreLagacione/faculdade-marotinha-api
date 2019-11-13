package com.lagacione.faculdademarotinhaapi.domain;

import javax.persistence.*;

@Entity
@Table(name = "boletim")
public class Boletim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
