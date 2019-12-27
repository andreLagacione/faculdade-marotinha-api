package com.lagacione.faculdademarotinhaapi.nota.repository;

import com.lagacione.faculdademarotinhaapi.nota.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRespository extends JpaRepository<Nota, Integer> {

    @Query("SELECT nota FROM Nota as nota WHERE nota.idBoletim = :idBoletim")
    public List<Nota> obterNotaByIdBoletim(@Param("idBoletim") Integer idBoletim);

    @Query("SELECT nota FROM Nota as nota WHERE nota.materia.id = :idMateria")
    public List<Nota> obterNotasByIdMateria(@Param("idMateria") Integer idMateria);

}
