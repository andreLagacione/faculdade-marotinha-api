package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNotaBimestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatreriaNotaBimestreRespository extends JpaRepository<MateriaNotaBimestre, Integer> {
}
