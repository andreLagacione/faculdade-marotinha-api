package com.lagacione.faculdademarotinhaapi.repositories;

import com.lagacione.faculdademarotinhaapi.domain.MateriaNotaBimestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MatreriaNotaBimestreRespository extends JpaRepository<MateriaNotaBimestre, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT nota FROM MateriaNotaBimestre as nota WHERE nota.idBoletim = :idBoletim")
    public List<MateriaNotaBimestre> obterMateriaByIdBoletim(@Param("idBoletim") Integer idBoletim);
}
