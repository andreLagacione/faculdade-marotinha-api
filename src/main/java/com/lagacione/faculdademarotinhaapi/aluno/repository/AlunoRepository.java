package com.lagacione.faculdademarotinhaapi.aluno.repository;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query("SELECT a FROM Aluno a WHERE a.cpf = :cpf")
    public Optional<Aluno> pesquisarCpf(@Param("cpf") String cpf);

}
