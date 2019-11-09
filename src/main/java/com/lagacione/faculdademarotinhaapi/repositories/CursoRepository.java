package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
