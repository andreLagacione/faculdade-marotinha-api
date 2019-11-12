package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
    @Transactional(readOnly = true)
    @Query("SELECT COUNT(*) FROM Aluno a WHERE a.cpf = :cpf")
    public Integer pesquisarCpf(@Param("cpf") String cpf);
}
