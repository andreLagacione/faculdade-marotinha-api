package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Transactional(readOnly = true)
    @Query("SELECT COUNT(*) FROM Professor p WHERE p.cpf = :cpf")
    public Integer pesquisarCpf(@Param("cpf") String cpf);
}
