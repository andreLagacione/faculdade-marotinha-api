package com.lagacione.faculdademarotinhaapi.boletim.specification;

import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class BoletimSpecification {

    public Specification<Boletim> filtroTelaBoletim(BoletimFilter filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (filter.getAno() != null) {
                predicates.add(criteriaBuilder.equal(root.get("ano"), filter.getAno()));
            }

            if (filter.getProfessor() != null) {
                predicates.add(criteriaBuilder.equal(root.get("professor"), filter.getProfessor()));
            }

            if (filter.getAluno() != null) {
                predicates.add(criteriaBuilder.equal(root.get("aluno"), filter.getAluno()));
            }

            if (filter.getTurma() != null) {
                predicates.add(criteriaBuilder.equal(root.get("turma"), filter.getTurma()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

}
