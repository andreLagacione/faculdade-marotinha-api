package com.lagacione.faculdademarotinhaapi.turma.specification;

import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class TurmaSpecification {

    public Specification<Turma> buildFilter(TurmaFilter filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (filter.getAno() != null) {
                predicates.add(root.get("ano").in(filter.getAno()));
            }

            if (filter.getIdCurso() != null) {
                predicates.add(root.get("curso").in(filter.getIdCurso()));
            }

            if (filter.getIdProfessor() != null) {
                predicates.add(root.get("professor").in(filter.getIdProfessor()));
            }

            if (filter.getPeriodo() != null) {
                predicates.add(root.get("periodo").in(filter.getPeriodo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));

        };
    }

}
