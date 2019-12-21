package com.lagacione.faculdademarotinhaapi.materia.service;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materia.repository.MateriaRepository;
import com.lagacione.faculdademarotinhaapi.nota.service.NotaService;
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
    private NotaService notaService;

    @Autowired
    public void MateriaService(MateriaRepository materiaRepository, NotaService notaService) {
        this.materiaRepository = materiaRepository;
        this.notaService = notaService;
    }

    public List<MateriaDTO> findAll() {
        List<Materia> materias = this.materiaRepository.findAll();
        List<MateriaDTO> materiasDTO = materias.stream().map(materia -> this.materiaDTOofMateria(materia)).collect(Collectors.toList());
        return materiasDTO;
    }

    public Page<MateriaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Materia> materias = this.materiaRepository.findAll(pageRequest);
        Page<MateriaDTO> materiasDTO = materias.map(materia -> this.materiaDTOofMateria(materia));
        return materiasDTO;
    }

    public MateriaDTO find(Integer id) throws ObjectNotFoundException {
        Optional<Materia> materia = this.materiaRepository.findById(id);

        if (!materia.isPresent()) {
            throw new ObjectNotFoundException("Matéria não encontrada!");
        }

        return this.materiaDTOofMateria(materia.get());
    }

    private MateriaDTO insert(Materia materia) {
        materia.setId(null);
        return this.materiaDTOofMateria(this.materiaRepository.save(materia));
    }

    private MateriaDTO update(Materia materia) throws ObjectNotFoundException {
        Materia newMateria = this.materiaOfMateriaDTO(this.find(materia.getId()));
        this.updateData(newMateria, materia);
        return this.materiaDTOofMateria(this.materiaRepository.save(newMateria));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            if (this.notaService.verificarSePodeRemoverNotas(id).size() > 0) {
                throw new ActionNotAllowedException("Não é possível remover essa matéria pois existem notas atreladas a ela!");
            }

            this.materiaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover essa cidade, pois existem registros atrelados a ela!");
        }
    }

    private void updateData(Materia newMateria, Materia materia) {
        newMateria.setName(materia.getName());
    }

    public MateriaDTO salvarRegistro(MateriaDTO materiaDTO, Boolean adicionar) throws ObjectNotFoundException {
        this.validarMateria(materiaDTO);
        Materia materia = this.materiaOfMateriaDTO(materiaDTO);

        if (adicionar) {
            return this.insert(materia);
        }

        return this.update(materia);
    }

    private void validarMateria(MateriaDTO materiaDTO) throws ActionNotAllowedException {
        Optional<Materia> materia = this.materiaRepository.validarMateria(materiaDTO.getName());

        if (materia.isPresent() && materia.get().getId() != materiaDTO.getId()) {
            throw new ActionNotAllowedException("Já existe uma matéria cadastrada com esse nome!");
        }
    }

    public Materia materiaOfMateriaDTO(MateriaDTO materiaDTO) {
        Materia materia = new Materia();
        materia.setId(materiaDTO.getId());
        materia.setName(materiaDTO.getName());
        return materia;
    }

    public MateriaDTO materiaDTOofMateria(Materia materia) {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(materia.getId());
        materiaDTO.setName(materia.getName());
        return materiaDTO;
    }
}
