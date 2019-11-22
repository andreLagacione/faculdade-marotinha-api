package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MateriaNotaRepository extends JpaRepository<MateriaNota, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT nota FROM MateriaNota as nota WHERE nota.aluno.id = :idAluno")
    public List<MateriaNota> getNotasByAluno(@Param("idAluno") Integer idAluno);
}
