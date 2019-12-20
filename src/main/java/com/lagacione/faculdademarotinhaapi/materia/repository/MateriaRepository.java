package com.lagacione.faculdademarotinhaapi.materia.repository;

import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Integer> {

    @Query("SELECT m FROM Materia m WHERE m.name = :name")
    public Optional<Materia> validarMateria(@Param("name") String name);

}
