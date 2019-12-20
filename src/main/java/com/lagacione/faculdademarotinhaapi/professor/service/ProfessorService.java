package com.lagacione.faculdademarotinhaapi.professor.service;

import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoListaDTO;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materia.service.MateriaService;
import com.lagacione.faculdademarotinhaapi.professor.entity.Professor;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorListaDTO;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorToEditDTO;
import com.lagacione.faculdademarotinhaapi.professor.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<ProfessorListaDTO> professorLista = professores.stream().map(professor -> this.professorListaDTOofEntity(professor)).collect(Collectors.toList());
        return professorLista;
    }

    public Page<ProfessorListaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Professor> professores = this.professorRepository.findAll(pageRequest);
        Page<ProfessorListaDTO> professorLista = professores.map(professor -> this.professorListaDTOofEntity(professor));
        return professorLista;
    }

    private Professor getProfessor(Integer id) throws ObjectNotFoundException {
        Optional<Professor> professor = this.professorRepository.findById(id);

        if (!professor.isPresent()) {
            throw new ObjectNotFoundException("Professor não encontrado!");
        }

        return professor.get();
    }

    public ProfessorToEditDTO find(Integer id) throws ObjectNotFoundException {
        return this.professorToEditDTOofEntity(this.getProfessor(id));
    }

    public ProfessorDTO findOptional(Integer id) throws ObjectNotFoundException {
        return this.professorDTOofEntity(this.getProfessor(id));
    }

    private ProfessorDTO insert(Professor professor) {
        professor.setId(null);
        return this.professorDTOofEntity(this.professorRepository.save(professor));
    }

    private ProfessorDTO update(Professor professor) throws ObjectNotFoundException {
        Professor newProfessor = this.professorOfDTO(this.findOptional(professor.getId()));
        this.updateData(newProfessor, professor);
        return this.professorDTOofEntity(this.professorRepository.save(newProfessor));
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
    }

    public ProfessorDTO salvarRegistro(ProfessorDTO professorDTO, Boolean adicionar) throws ActionNotAllowedException {
        this.validarMaterias(professorDTO);
        this.validarCursos(professorDTO);
        this.validarCpf(professorDTO);
        Professor professor = this.professorOfDTO(professorDTO);

        if (adicionar) {
            return this.insert(professor);
        }

        return this.update(professor);
    }

    private void validarMaterias(ProfessorDTO professorDTO) throws ObjectNotFoundException {
        List<MateriaDTO> materiasDTO = professorDTO.getMaterias().stream().map(id -> this.materiaService.find(id)).collect(Collectors.toList());

        if (materiasDTO.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos uma matéria!");
        }

        for (MateriaDTO materiaDTO : materiasDTO) {
            this.materiaService.find(materiaDTO.getId());
        }
    }

    private void validarCursos(ProfessorDTO professorDTO) throws ObjectNotFoundException {
        List<CursoDTO> cursosDTO = professorDTO.getCursos().stream().map(id -> this.cursoService.findOptional(id)).collect(Collectors.toList());

        if (cursosDTO.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos um curso!");
        }

        for (CursoDTO cursoDTO : cursosDTO) {
            this.cursoService.find(cursoDTO.getId());
        }
    }

    private void validarCpf(ProfessorDTO professorDTO) throws ActionNotAllowedException {
        Optional<Professor> professor = this.professorRepository.pesquisarCpf(professorDTO.getCpf());

        if (professor.isPresent() && professor.get().getId() != professorDTO.getId()) {
            throw new ActionNotAllowedException("Já existe um professor cadastrado com esse CPF. Por favor informe outro CPF!");
        }
    }

    public Professor professorOfDTO(ProfessorDTO professorDTO) {
        Professor professor = new Professor();
        List<MateriaDTO> materiasDTO = professorDTO.getMaterias().stream().map(id -> this.materiaService.find(id)).collect(Collectors.toList());
        List<Materia> materias = materiasDTO.stream().map(materia -> this.materiaService.materiaOfMateriaDTO(materia)).collect(Collectors.toList());

        professor.setId(professorDTO.getId());
        professor.setName(professorDTO.getName());
        professor.setAge(professorDTO.getAge());
        professor.setCpf(professorDTO.getCpf());
        professor.setPhone(professorDTO.getPhone());
        professor.setMateriasLecionadas(materias);
        return professor;
    }

    public ProfessorDTO professorDTOofEntity(Professor professor) {
        ProfessorDTO professorDTO = new ProfessorDTO();
        List<Integer> materias = professor.getMateriasLecionadas().stream().map(materia -> materia.getId()).collect(Collectors.toList());

        professorDTO.setId(professor.getId());
        professorDTO.setName(professor.getName());
        professorDTO.setAge(professor.getAge());
        professorDTO.setCpf(professor.getCpf());
        professorDTO.setPhone(professor.getPhone());
        professorDTO.setMaterias(materias);
        return professorDTO;
    }

    public ProfessorListaDTO professorListaDTOofEntity(Professor professor) {
        ProfessorListaDTO professorListaDTO = new ProfessorListaDTO();
        professorListaDTO.setId(professor.getId());
        professorListaDTO.setName(professor.getName());
        professorListaDTO.setAge(professor.getAge());
        professorListaDTO.setCpf(professor.getCpf());
        professorListaDTO.setPhone(professor.getPhone());
        return professorListaDTO;
    }

    public ProfessorToEditDTO professorToEditDTOofEntity(Professor professor) {
        ProfessorToEditDTO professorToEditDTO = new ProfessorToEditDTO();
        List<MateriaDTO> materias = professor.getMateriasLecionadas().stream().map(materia -> this.materiaService.materiaDTOofMateria(materia)).collect(Collectors.toList());

        professorToEditDTO.setId(professor.getId());
        professorToEditDTO.setName(professor.getName());
        professorToEditDTO.setAge(professor.getAge());
        professorToEditDTO.setCpf(professor.getCpf());
        professorToEditDTO.setPhone(professor.getPhone());
        professorToEditDTO.setMaterias(materias);
        return professorToEditDTO;
    }
}
