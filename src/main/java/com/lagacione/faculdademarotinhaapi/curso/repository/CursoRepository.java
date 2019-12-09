package com.lagacione.faculdademarotinhaapi.curso.repository;

import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
