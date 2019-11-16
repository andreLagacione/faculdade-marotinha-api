package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaNotaRepository extends JpaRepository<MateriaNota, Integer> {
}
