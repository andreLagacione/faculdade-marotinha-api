package com.lagacione.faculdademarotinhaapi.curso.service;

import com.lagacione.faculdademarotinhaapi.boletim.service.BoletimService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoListaDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoNomeListaDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoToEditDTO;
import com.lagacione.faculdademarotinhaapi.curso.repository.CursoRepository;
import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materia.service.MateriaService;
import com.lagacione.faculdademarotinhaapi.turma.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {
    private CursoRepository cursoRepository;
    private MateriaService materiaService;
    private TurmaService turmaService;

    @Autowired
    public void CursoService(CursoRepository cursoRepository, MateriaService materiaService, TurmaService turmaService) {
        this.cursoRepository = cursoRepository;
        this.materiaService = materiaService;
        this.turmaService = turmaService;
    }

    public List<CursoListaDTO> findAll() {
        List<Curso> cursos = this.cursoRepository.findAll();
        List<CursoListaDTO> cursosLista = cursos.stream().map(curso -> this.cursoListaDTOofCurso(curso)).collect(Collectors.toList());
        return cursosLista;
    }

    public Page<CursoListaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Curso> cursos = this.cursoRepository.findAll(pageRequest);
        Page<CursoListaDTO> cursosLista = cursos.map(curso -> this.cursoListaDTOofCurso(curso));
        return cursosLista;
    }

    public Curso getCurso(Integer id) throws ObjectNotFoundException {
        Optional<Curso> curso = this.cursoRepository.findById(id);

        if (!curso.isPresent()) {
            throw new ObjectNotFoundException("Curso não encontrado!");
        }

        return curso.get();
    }

    public CursoToEditDTO find(Integer id) {
        return this.cursoToEditDTOofCurso(this.getCurso(id));
    }

    public CursoDTO findOptional(Integer id) throws ObjectNotFoundException {
        return this.cursoDTOofCurso(this.getCurso(id));
    }

    private CursoDTO insert(Curso curso) {
        curso.setId(null);
        return this.cursoDTOofCurso(this.cursoRepository.save(curso));
    }

    private CursoDTO update(Curso curso) throws ObjectNotFoundException {
        Curso newCurso = this.cursoOfCursoDTO(this.findOptional(curso.getId()));
        this.updateData(newCurso, curso);
        return this.cursoDTOofCurso(this.cursoRepository.save(newCurso));
    }

    public void delete(Integer id) {
        this.find(id);

        if (this.turmaService.findTurmaByCursoId(id).size() > 0) {
            throw new ActionNotAllowedException("Existem turmas atreladas a esse curso!");
        }

        this.cursoRepository.deleteById(id);
    }

    private void updateData(Curso newCurso, Curso curso) {
        newCurso.setName(curso.getName());
        newCurso.setMaterias(curso.getMaterias());
    }

    public CursoDTO salvarRegistro(CursoDTO cursoDTO, Boolean adicionar) throws ActionNotAllowedException {
        Curso curso = this.cursoOfCursoDTO(cursoDTO);
        this.validarCurso(cursoDTO);
        this.validarMaterias(curso);

        if (adicionar) {
            return this.insert(curso);
        }

        return this.update(curso);
    }

    private void validarCurso(CursoDTO cursoDTO) throws ActionNotAllowedException {
       Optional<Curso> curso = this.cursoRepository.validarCurso(cursoDTO.getName());

       if (curso.isPresent() && curso.get().getId() != cursoDTO.getId()) {
           throw new ActionNotAllowedException("Já existe um curso cadastrado com esse nome!");
       }
    }

    private void validarMaterias(Curso curso) throws ActionNotAllowedException {
        List<Materia> materiasCurso = curso.getMaterias();

        if (materiasCurso.size() == 0) {
            throw new ActionNotAllowedException("Informe pelo menos uma matéria.");
        }

        for (Materia materia : materiasCurso) {
            this.materiaService.find(materia.getId());
        }
    }

    public Curso cursoOfCursoDTO(CursoDTO cursoDTO) {
        Curso curso = new Curso();
        curso.setId(cursoDTO.getId());
        List<MateriaDTO> materiasDTO = cursoDTO.getMaterias().stream().map(id -> this.materiaService.find(id)).collect(Collectors.toList());
        List<Materia> materias = materiasDTO.stream().map(materia -> this.materiaService.materiaOfMateriaDTO(materia)).collect(Collectors.toList());
        curso.setMaterias(materias);
        curso.setName(cursoDTO.getName());
        return curso;
    }

    public CursoDTO cursoDTOofCurso(Curso curso) {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        List<Integer> materias = curso.getMaterias().stream().map(materia -> materia.getId()).collect(Collectors.toList());
        cursoDTO.setMaterias(materias);
        cursoDTO.setName(curso.getName());
        return cursoDTO;
    }

    public CursoListaDTO cursoListaDTOofCurso(Curso curso) {
        CursoListaDTO cursoListaDTO = new CursoListaDTO();
        cursoListaDTO.setId(curso.getId());
        cursoListaDTO.setNome(curso.getName());
        return cursoListaDTO;
    }

    public CursoNomeListaDTO cursoNomeListaDTOofCurso(Curso curso) {
        CursoNomeListaDTO cursoNomeListaDTO = new CursoNomeListaDTO();
        cursoNomeListaDTO.setId(curso.getId());
        cursoNomeListaDTO.setNome(curso.getName());
        return cursoNomeListaDTO;
    }

    public CursoToEditDTO cursoToEditDTOofCurso(Curso curso) {
        CursoToEditDTO cursoToEditDTO = new CursoToEditDTO();
        cursoToEditDTO.setId(curso.getId());
        cursoToEditDTO.setNome(curso.getName());
        List<MateriaDTO> materias = curso.getMaterias().stream().map(materia -> this.materiaService.materiaDTOofMateria(materia)).collect(Collectors.toList());
        cursoToEditDTO.setMaterias(materias);
        return cursoToEditDTO;
    }
}
