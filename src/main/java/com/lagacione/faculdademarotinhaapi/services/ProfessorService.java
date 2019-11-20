package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Professor;
import com.lagacione.faculdademarotinhaapi.dto.*;
import com.lagacione.faculdademarotinhaapi.repositories.ProfessorRepository;
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
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private CursoService cursoService;

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

        if (professor == null) {
            throw new ObjectNotFoundException("Professor não encontrado!");
        }

        return ProfessorToEditDTO.of(professor.get());
    }

    public Professor findForUpdate(Integer id) throws ObjectNotFoundException {
        Optional<Professor> professor = this.professorRepository.findById(id);
        return professor.orElseThrow(() -> new ObjectNotFoundException("Professor não encontrado!"));
    }

    private Professor insert(Professor professor) {
        professor.setId(null);
        return this.professorRepository.save(professor);
    }

    private Professor update(Professor professor) throws ObjectNotFoundException {
        Professor newProfessor = this.findForUpdate(professor.getId());
        this.updateData(newProfessor, professor);
        return this.professorRepository.save(newProfessor);
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

    public Professor salvarRegistro(ProfessorDTO professorDTO, Boolean adicionar) throws Exception {
        this.validarMaterias(professorDTO);
        this.validarCursos(professorDTO);
        Professor professor = Professor.of(professorDTO);

        if (adicionar) {
            this.validarCpf(professorDTO);
            return this.insert(professor);
        }

        return this.update(professor);
    }

    private void validarMaterias(ProfessorDTO professorDTO) {
        List<MateriaDTO> materiasDTO = professorDTO.getMateriasLecionadas();

        if (materiasDTO == null || materiasDTO.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos uma matéria!");
        }

        for (MateriaDTO materiaDTO : materiasDTO) {
            this.materiaService.find(materiaDTO.getId());
        }
    }

    private void validarCursos(ProfessorDTO professorDTO) {
        List<CursoDTO> cursosDTO = professorDTO.getCursosLecionados();

        if (cursosDTO == null || cursosDTO.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos um curso!");
        }

        for (CursoDTO cursoDTO : cursosDTO) {
            this.cursoService.find(cursoDTO.getId());
        }
    }

    private void validarCpf(ProfessorDTO professorDTO) throws Exception {
        Integer validar = this.professorRepository.pesquisarCpf(professorDTO.getCpf());

        if (validar > 0) {
            throw new Exception("Já existe um professor cadastrado com esse CPF. Por favor informe outro CPF!");
        }
    }
}
