package com.lagacione.faculdademarotinhaapi.curso.repository;

import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    @Query("SELECT c FROM Curso c WHERE c.name = :name")
    public Optional<Curso> validarCurso(@Param("name") String name);

}
