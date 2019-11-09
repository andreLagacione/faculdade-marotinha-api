package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Curso;
import com.lagacione.faculdademarotinhaapi.domain.Materia;
import com.lagacione.faculdademarotinhaapi.dto.CursoDTO;
import com.lagacione.faculdademarotinhaapi.repositories.CursoRepository;
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
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private MateriaService materiaService;

    public List<CursoDTO> findAll() {
        List<Curso> cursos = this.cursoRepository.findAll();
        List<CursoDTO> cursosDTO = cursos.stream().map(obj -> new CursoDTO(obj)).collect(Collectors.toList());
        return cursosDTO;
    }

    public Page<CursoDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
        Page<Curso> cursos = this.cursoRepository.findAll(pageRequest);
        Page<CursoDTO> cursosDTO = cursos.map(obj -> new CursoDTO(obj));
        return cursosDTO;
    }

    public Curso find(Integer id) throws ObjectNotFoundException {
        Optional<Curso> curso = this.cursoRepository.findById(id);
        return curso.orElseThrow(() -> new ObjectNotFoundException("Curso não encontrado!"));
    }

    private Curso insert(Curso curso) {
        curso.setId(null);
        return this.cursoRepository.save(curso);
    }

    private Curso update(Curso curso) throws ObjectNotFoundException {
        Curso newCurso = this.find(curso.getId());
        this.updateData(newCurso, curso);
        return this.cursoRepository.save(newCurso);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.cursoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover esse curso!");
        }
    }

    public Curso fromDto(CursoDTO cursoDTO) {
        return new Curso(
                cursoDTO.getId(),
                cursoDTO.getName(),
                cursoDTO.getMaterias()
        );
    }

    private void updateData(Curso newCurso, Curso curso) {
        newCurso.setName(curso.getName());
        newCurso.setMaterias(curso.getMaterias());
    }

    public Curso salvarRegistro(CursoDTO cursoDTO, Boolean adicionar) {
        Curso curso = this.fromDto(cursoDTO);
        this.adicionarMaterias(curso);

        if (adicionar) {
            return this.insert(curso);
        }

        return this.update(curso);
    }

    private void adicionarMaterias(Curso curso) {
        List<Materia> materiasCurso = curso.getMaterias();

        if (materiasCurso == null || materiasCurso.size() == 0) {
            throw new ObjectNotFoundException("Informe pelo menos uma matéria.");
        }

        for (Materia materia : materiasCurso) {
            this.materiaService.find(materia.getId());
        }
    }
}
