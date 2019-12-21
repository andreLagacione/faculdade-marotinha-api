package com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.repository;

import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.entity.MateriaNotaBimestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatreriaNotaBimestreRespository extends JpaRepository<MateriaNotaBimestre, Integer> {

    @Query("SELECT nota FROM MateriaNotaBimestre as nota WHERE nota.idBoletim = :idBoletim")
    public List<MateriaNotaBimestre> obterMateriaByIdBoletim(@Param("idBoletim") Integer idBoletim);

    @Query("SELECT nota FROM MateriaNotaBimestre as nota WHERE nota.materia.id = :idMateria")
    public List<MateriaNotaBimestre> obterMateriaByIdMateria(@Param("idMateria") Integer idMateria);
}
