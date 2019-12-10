package com.lagacione.faculdademarotinhaapi.materia.service;

import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materia.repository.MateriaRepository;
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
public class MateriaService {
    private MateriaRepository materiaRepository;

    @Autowired
    public void MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    public List<MateriaDTO> findAll() {
        List<Materia> materias = this.materiaRepository.findAll();
        List<MateriaDTO> materiasDTO = materias.stream().map(MateriaDTO::of).collect(Collectors.toList());
        return materiasDTO;
    }

    public Page<MateriaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Materia> materias = this.materiaRepository.findAll(pageRequest);
        Page<MateriaDTO> materiasDTO = materias.map(MateriaDTO::of);
        return materiasDTO;
    }

    public MateriaDTO find(Integer id) throws ObjectNotFoundException {
        Optional<Materia> materia = this.materiaRepository.findById(id);

        if (!materia.isPresent()) {
            throw new ObjectNotFoundException("Matéria não encontrada!");
        }

        return MateriaDTO.of(materia.get());
    }

    private MateriaDTO insert(Materia materia) {
        materia.setId(null);
        return MateriaDTO.of(this.materiaRepository.save(materia));
    }

    private MateriaDTO update(Materia materia) throws ObjectNotFoundException {
        Materia newMateria = Materia.of(this.find(materia.getId()));
        this.updateData(newMateria, materia);
        return MateriaDTO.of(this.materiaRepository.save(newMateria));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.materiaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover essa cidade, pois existem registros atrelados a ela!");
        }
    }

    private void updateData(Materia newMateria, Materia materia) {
        newMateria.setName(materia.getName());
    }

    public MateriaDTO salvarRegistro(MateriaDTO materiaDTO, Boolean adicionar) throws ObjectNotFoundException {
        Materia materia = Materia.of(materiaDTO);

        if (adicionar) {
            return this.insert(materia);
        }

        return this.update(materia);
    }
}
