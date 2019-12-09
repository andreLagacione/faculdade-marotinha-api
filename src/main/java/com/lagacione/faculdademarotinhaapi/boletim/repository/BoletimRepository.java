package com.lagacione.faculdademarotinhaapi.boletim.repository;

import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletimRepository extends JpaRepository<Boletim, Integer> {
}
