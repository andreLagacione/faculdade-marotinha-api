package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Curso;
import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.domain.Professor;
import com.lagacione.faculdademarotinhaapi.dto.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.repositories.ProfessorRepository;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

    public List<ProfessorDTO> findAll() {
        List<Professor> professores = this.professorRepository.findAll();
        List<ProfessorDTO> professoresDTO = professores.stream().map(
                obj -> new ProfessorDTO(obj.getName(), obj.getAge(), obj.getCpf(), obj.getPhone(), obj.getId(), obj.getMateriasLecionadas(), obj.getCursosLecionados())
        ).collect(Collectors.toList());
        return professoresDTO;
    }

    public Page<ProfessorDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        Page<Professor> professores = this.professorRepository.findAll(pageRequest);
        Page<ProfessorDTO> professoresDTO = professores.map(
                obj -> new ProfessorDTO(obj.getName(), obj.getAge(), obj.getCpf(), obj.getPhone(), obj.getId(), obj.getMateriasLecionadas(), obj.getCursosLecionados())
        );
        return professoresDTO;
    }

    public Professor find(Integer id) throws ObjectNotFoundException {
        Optional<Professor> professor = this.professorRepository.findById(id);
        return professor.orElseThrow(() -> new ObjectNotFoundException("Professor não encontrado!"));
    }

    private Professor insert(Professor professor) {
        professor.setId(null);
        return this.professorRepository.save(professor);
    }

    private Professor update(Professor professor) throws ObjectNotFoundException {
        Professor newProfessor = this.find(professor.getId());
        this.updateData(newProfessor, professor);
        return this.professorRepository.save(newProfessor);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.professorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover este professo, pois existem registros atrelados a ele!");
        }
    }

    public Professor fromDto(ProfessorDTO professorDTO) {
        return new Professor(
                professorDTO.getName(),
                professorDTO.getAge(),
                professorDTO.getCpf(),
                professorDTO.getPhone(),
                professorDTO.getId(),
                professorDTO.getMateriasLecionadas(),
                professorDTO.getCursosLecionados()
        );
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

    public Professor salvarRegistro(ProfessorDTO professorDTO, Boolean adicionar) throws ObjectNotFoundException {
        this.validarMaterias(professorDTO);
        this.validarCursos(professorDTO);
        Professor professor = this.fromDto(professorDTO);

        if (adicionar) {
            return this.insert(professor);
        }

        return this.update(professor);
    }

    private void validarMaterias(ProfessorDTO professorDTO) {
        List<Materia> materias = professorDTO.getMateriasLecionadas();

        if (materias == null || materias.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos uma matéria!");
        }

        for (Materia materia : materias) {
            this.materiaService.find(materia.getId());
        }
    }

    private void validarCursos(ProfessorDTO professorDTO) {
        List<Curso> cursos = professorDTO.getCursosLecionados();

        if (cursos == null || cursos.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos um curso!");
        }

        for (Curso curso : cursos) {
            this.cursoService.find(curso.getId());
        }
    }
}
