package com.lagacione.faculdademarotinhaapi.professor.service;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materia.service.MateriaService;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorListaDTO;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorToEditDTO;
import com.lagacione.faculdademarotinhaapi.professor.repository.ProfessorRepository;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
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
public class ProfessorService {
    private ProfessorRepository professorRepository;
    private MateriaService materiaService;
    private CursoService cursoService;

    @Autowired
    public void ProfessorService(ProfessorRepository professorRepository, MateriaService materiaService, CursoService cursoService) {
        this.professorRepository = professorRepository;
        this.materiaService = materiaService;
        this.cursoService = cursoService;
    }

    public List<ProfessorListaDTO> findAll() {
        List<Professor> professores = this.professorRepository.findAll();
        List<ProfessorListaDTO> professorLista = professores.stream().map(ProfessorListaDTO::of).collect(Collectors.toList());
        return professorLista;
    }

    public Page<ProfessorListaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Professor> professores = this.professorRepository.findAll(pageRequest);
        Page<ProfessorListaDTO> professorLista = professores.map(ProfessorListaDTO::of);
        return professorLista;
    }

    public ProfessorToEditDTO find(Integer id) throws ObjectNotFoundException {
        Optional<Professor> professor = this.professorRepository.findById(id);

        if (!professor.isPresent()) {
            throw new ObjectNotFoundException("Professor não encontrado!");
        }

        return ProfessorToEditDTO.of(professor.get());
    }

    public ProfessorDTO findOptional(Integer id) throws ObjectNotFoundException {
        Optional<Professor> professor = this.professorRepository.findById(id);

        if (!professor.isPresent()) {
            throw new ObjectNotFoundException("Professor não encontrado!");
        }

        return ProfessorDTO.of(professor.get());
    }

    private ProfessorDTO insert(Professor professor) {
        professor.setId(null);
        return ProfessorDTO.of(this.professorRepository.save(professor));
    }

    private ProfessorDTO update(Professor professor) throws ObjectNotFoundException {
        Professor newProfessor = Professor.of(this.findOptional(professor.getId()));
        this.updateData(newProfessor, professor);
        return ProfessorDTO.of(this.professorRepository.save(newProfessor));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.professorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover este professor, pois existem registros atrelados a ele!");
        }
    }

    private void updateData(Professor newProfessor, Professor professor) {
        newProfessor.setName(professor.getName());
        newProfessor.setAge(professor.getAge());
        newProfessor.setCpf(professor.getCpf());
        newProfessor.setPhone(professor.getPhone());
        newProfessor.setId(professor.getId());
        newProfessor.setMateriasLecionadas(professor.getMateriasLecionadas());
        newProfessor.setCursosLecionados(professor.getCursosLecionados());
    }

    public ProfessorDTO salvarRegistro(ProfessorDTO professorDTO, Boolean adicionar) throws ActionNotAllowedException {
        this.validarMaterias(professorDTO);
        this.validarCursos(professorDTO);
        Professor professor = Professor.of(professorDTO);

        if (adicionar) {
            this.validarCpf(professorDTO);
            return this.insert(professor);
        }

        return this.update(professor);
    }

    private void validarMaterias(ProfessorDTO professorDTO) throws ObjectNotFoundException {
        List<MateriaDTO> materiasDTO = professorDTO.getMateriasLecionadas();

        if (materiasDTO.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos uma matéria!");
        }

        for (MateriaDTO materiaDTO : materiasDTO) {
            this.materiaService.find(materiaDTO.getId());
        }
    }

    private void validarCursos(ProfessorDTO professorDTO) throws ObjectNotFoundException {
        List<CursoDTO> cursosDTO = professorDTO.getCursosLecionados();

        if (cursosDTO.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos um curso!");
        }

        for (CursoDTO cursoDTO : cursosDTO) {
            this.cursoService.find(cursoDTO.getId());
        }
    }

    private void validarCpf(ProfessorDTO professorDTO) throws ActionNotAllowedException {
        Optional<Professor> professor = this.professorRepository.pesquisarCpf(professorDTO.getCpf());

        if (professor.isPresent()) {
            throw new ActionNotAllowedException("Já existe um professor cadastrado com esse CPF. Por favor informe outro CPF!");
        }
    }
}
