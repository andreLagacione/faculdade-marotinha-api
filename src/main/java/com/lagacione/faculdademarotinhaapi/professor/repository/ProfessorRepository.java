package com.lagacione.faculdademarotinhaapi.professor.repository;

import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    @Query("SELECT p FROM Professor p WHERE p.cpf = :cpf")
    public Optional<Professor> pesquisarCpf(@Param("cpf") String cpf);

}
