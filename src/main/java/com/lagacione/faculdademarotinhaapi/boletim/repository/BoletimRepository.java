package com.lagacione.faculdademarotinhaapi.boletim.repository;

import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoletimRepository extends JpaRepository<Boletim, Integer>, JpaSpecificationExecutor<Boletim> {

    @Query("SELECT b FROM Boletim b WHERE b.ano = :ano AND b.professor.id = :idProfessor AND b.aluno.id = :idAluno AND b.turma.id = :idTurma")
    public Optional<Boletim> validarSeBoletimJaExiste(
            @Param("ano") Integer ano,
            @Param("idProfessor") Integer idProfessor,
            @Param("idAluno") Integer idAluno,
            @Param("idTurma") Integer idTurma
    );

    @Query("SELECT b FROM Boletim b WHERE b.aluno.id = :idAluno")
    public List<Boletim> getBoletinsByAlunoId(@Param("idAluno") Integer idAluno);

    @Query("SELECT b FROM Boletim b WHERE b.turma.id = :idTurma")
    public List<Boletim> getBoletinsByTurmaId(@Param("idTurma") Integer idTurma);

    @Query("SELECT b FROM Boletim b WHERE b.professor.id = :idProfessor")
    public List<Boletim> getBoletinsByProfessorId(@Param("idProfessor") Integer idProfessor);
}
