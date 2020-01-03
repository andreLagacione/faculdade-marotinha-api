package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.nota.entity.Nota;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.List;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Boletim.class)
public abstract class Boletim_ {

    public static volatile SingularAttribute<Boletim, Integer> id;
    public static volatile SingularAttribute<Boletim, Integer> ano;
    public static volatile SingularAttribute<Boletim, Professor> professor;
    public static volatile SingularAttribute<Boletim, Aluno> aluno;
    public static volatile SingularAttribute<Boletim, Turma> turma;
    public static volatile SingularAttribute<Boletim, Nota> notas;

    public static final String ID = "id";
    public static final String ANO = "ano";
    public static final String PROFESSOR = "professor";
    public static final String ALUNO = "aluno";
    public static final String TURMA = "turma";
    public static final String NOTAS = "notas";

}
