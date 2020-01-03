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
                predicates.add(root.get("ano").in(filter.getAno()));
            }

            if (filter.getIdProfessor() != null) {
                predicates.add(root.get("professor").in(filter.getIdProfessor()));
            }

            if (filter.getIdAluno() != null) {
                predicates.add(root.get("aluno").in(filter.getIdAluno()));
            }

            if (filter.getIdTurma() != null) {
                predicates.add(root.get("turma").in(filter.getIdTurma()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

}
