package com.lagacione.faculdademarotinhaapi.curso.repository;

import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
