package com.lagacione.faculdademarotinhaapi.boletim.model;

import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import org.springframework.data.jpa.domain.Specification;

public class BoletimSpecification {

    public static Specification<Boletim> ano(Integer ano) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Boletim_.ano), ano);
        };
    }
}
