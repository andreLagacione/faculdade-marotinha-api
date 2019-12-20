package com.lagacione.faculdademarotinhaapi.turma.repository;

import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    @Query("SELECT t FROM Turma t WHERE t.ano = :ano AND t.curso.id = :idCurso AND t.professor.id = :idProfessor AND t.periodo = :periodo")
    public Optional<Turma> validarTurma(
            @Param("ano") Integer ano,
            @Param("idCurso") Integer idCurso,
            @Param("idProfessor") Integer idProfessor,
            @Param("periodo") String periodo
    );

}
