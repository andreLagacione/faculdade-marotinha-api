package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.domain.MateriaNotaBimestre;
import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaBimestreDTO;
import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaBimestreListDTO;
import com.lagacione.faculdademarotinhaapi.repositories.MatreriaNotaBimestreRespository;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaNotaBimestreService {
    @Autowired
    private MatreriaNotaBimestreRespository matreriaNotaBimestreRespository;

    @Autowired
    private MateriaService materiaService;

    public List<MateriaNotaBimestreListDTO> findAll() {
        List<MateriaNotaBimestre> notas = this.matreriaNotaBimestreRespository.findAll();
        List<MateriaNotaBimestreListDTO> notasDTO = notas.stream().map(MateriaNotaBimestreListDTO::of).collect(Collectors.toList());
        return notasDTO;
    }

    public Page<MateriaNotaBimestreListDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<MateriaNotaBimestre> notas = this.matreriaNotaBimestreRespository.findAll(pageRequest);
        Page<MateriaNotaBimestreListDTO> notasDTO = notas.map(MateriaNotaBimestreListDTO::of);
        return notasDTO;
    }

    public MateriaNotaBimestreDTO find(Integer id) throws ObjectNotFoundException {
        Optional<MateriaNotaBimestre> nota = this.matreriaNotaBimestreRespository.findById(id);

        if (nota != null) {
            MateriaNotaBimestreDTO notaDTO = new MateriaNotaBimestreDTO();
            notaDTO = notaDTO.of(nota.get());
            return notaDTO;
        }

        throw new ObjectNotFoundException("Nota não encontrada!");
    }

    private MateriaNotaBimestreDTO insert(MateriaNotaBimestre nota) {
        nota.setId(null);
        return MateriaNotaBimestreDTO.of(this.matreriaNotaBimestreRespository.save(nota));
    }

    private MateriaNotaBimestreDTO update(MateriaNotaBimestre nota) throws ObjectNotFoundException {
        MateriaNotaBimestre newNota = MateriaNotaBimestre.of(this.find(nota.getId()));
        this.updateData(newNota, nota);
        return MateriaNotaBimestreDTO.of(this.matreriaNotaBimestreRespository.save(newNota));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.matreriaNotaBimestreRespository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover esta nota!");
        }
    }

    private void updateData(MateriaNotaBimestre newNota, MateriaNotaBimestre nota) {
        newNota.setId(nota.getId());
        newNota.setMateria(nota.getMateria());
        newNota.setNotaBimestre1(nota.getNotaBimestre1());
        newNota.setNotaBimestre2(nota.getNotaBimestre2());
        newNota.setNotaBimestre3(nota.getNotaBimestre3());
        newNota.setNotaBimestre4(nota.getNotaBimestre4());
    }

    public MateriaNotaBimestreDTO salvarRegistro(MateriaNotaBimestreDTO notaDTO, Boolean adicionar) throws Exception {
        this.validarMateria(notaDTO);

        MateriaNotaBimestre nota = MateriaNotaBimestre.of(notaDTO);

        if (adicionar) {
            return this.insert(nota);
        }

        return this.update(nota);
    }

    private void validarMateria(MateriaNotaBimestreDTO notaDTO) {
        Materia materia = notaDTO.getMateria();

        if (materia == null) {
            throw new ObjectNotFoundException("Informe a matéria!");
        }

        this.materiaService.find(materia.getId());
    }
}
