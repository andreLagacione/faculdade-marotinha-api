package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.dto.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.repositories.MateriaRepository;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaService {
    @Autowired
    private MateriaRepository materiaRepository;

    public List<MateriaDTO> findAll() {
        List<Materia> materias = this.materiaRepository.findAll();
        List<MateriaDTO> materiasDTO = materias.stream().map(obj -> new MateriaDTO(obj)).collect(Collectors.toList());
        return materiasDTO;
    }

    public Page<MateriaDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<Materia> materias = this.materiaRepository.findAll(pageRequest);
        Page<MateriaDTO> materiasDTO = materias.map(obj -> new MateriaDTO(obj));
        return materiasDTO;
    }

    public Materia find(Integer id) throws ObjectNotFoundException {
        Optional<Materia> materia = this.materiaRepository.findById(id);
        return materia.orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada!"));
    }

    private Materia insert(Materia materia) {
        materia.setId(null);
        return this.materiaRepository.save(materia);
    }

    private Materia update(Materia materia) throws ObjectNotFoundException {
        Materia newMateria = this.find(materia.getId());
        this.updateData(newMateria, materia);
        return this.materiaRepository.save(newMateria);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.materiaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover essa cidade, pois existem registros atrelados a ela!");
        }
    }

    public Materia fromDto(MateriaDTO MateriaDTO) {
        return new Materia(MateriaDTO.getId(), MateriaDTO.getName());
    }

    private void updateData(Materia newMateria, Materia materia) {
        newMateria.setName(materia.getName());
    }

    public Materia salvarRegistro(MateriaDTO materiaDTO, Boolean adicionar) throws ObjectNotFoundException {
        Materia materia = this.fromDto(materiaDTO);

        if (adicionar) {
            return this.insert(materia);
        }

        return this.update(materia);
    }
}
