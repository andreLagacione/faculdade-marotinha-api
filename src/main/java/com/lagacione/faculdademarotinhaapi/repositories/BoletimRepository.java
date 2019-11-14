package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.Boletim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletimRepository extends JpaRepository<Boletim, Integer> {
}
