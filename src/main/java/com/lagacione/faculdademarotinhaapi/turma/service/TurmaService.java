package com.lagacione.faculdademarotinhaapi.turma.service;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.aluno.service.AlunoService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.professor.service.ProfessorService;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaDTO;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaEditDTO;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaListDTO;
import com.lagacione.faculdademarotinhaapi.turma.repository.TurmaRepository;
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
public class TurmaService {
    private TurmaRepository turmaRepository;
    private CursoService cursoService;
    private ProfessorService professorService;
    private AlunoService alunoService;

    @Autowired
    public void TurmaService(TurmaRepository turmaRepository, CursoService cursoService, ProfessorService professorService, AlunoService alunoService) {
        this.turmaRepository = turmaRepository;
        this.cursoService = cursoService;
        this.professorService = professorService;
        this.alunoService = alunoService;
    }

    public List<TurmaListDTO> findAll() {
        List<Turma> turmas = this.turmaRepository.findAll();
        List<TurmaListDTO> turmaListDTO = turmas.stream().map(turma -> this.turmaListDTOofEntity(turma)).collect(Collectors.toList());
        return turmaListDTO;
    }

    public Page<TurmaListDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Turma> turmas = this.turmaRepository.findAll(pageRequest);
        Page<TurmaListDTO> alunoListaDTO = turmas.map(turma -> this.turmaListDTOofEntity(turma));
        return alunoListaDTO;
    }

    private Turma findTurma(Integer id) throws ObjectNotFoundException {
        Optional<Turma> turma = this.turmaRepository.findById(id);

        if (!turma.isPresent()) {
            throw new ObjectNotFoundException("Turma não encontrada!");
        }

        return turma.get();
    }

    public TurmaEditDTO find(Integer id) throws ObjectNotFoundException {
        return this.turmaEditDTOofEntity(this.findTurma(id));
    }

    private TurmaDTO insert(Turma turma) {
        turma.setId(null);
        return this.turmaDTOofEntity(this.turmaRepository.save(turma));
    }

    public TurmaDTO findTurmaDTO(Integer id) throws ObjectNotFoundException {
        return this.turmaDTOofEntity(this.findTurma(id));
    }

    private TurmaDTO update(Turma turma) throws ObjectNotFoundException {
        Turma newTurma = this.turmaOfTurmaDTO(this.findTurmaDTO(turma.getId()));
        this.updateData(newTurma, turma);
        return this.turmaDTOofEntity(this.turmaRepository.save(newTurma));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.turmaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover esta turma!");
        }
    }

    private void updateData(Turma newTurma, Turma turma) {
        newTurma.setAno(turma.getAno());
        newTurma.setCurso(turma.getCurso());
        newTurma.setProfessor(turma.getProfessor());
        newTurma.setAlunos(turma.getAlunos());
        newTurma.setPeriodo(turma.getPeriodo());
    }

    public TurmaDTO salvarRegistro(TurmaDTO turmaDTO, Boolean adicionar) throws ActionNotAllowedException {
        this.validarTurma(turmaDTO);

        Turma turma = this.turmaOfTurmaDTO(turmaDTO);

        if (adicionar) {
            return this.insert(turma);
        }

        return this.update(turma);
    }

    private void validarTurma(TurmaDTO turmaDTO) {
        Optional<Turma> turma = this.turmaRepository.validarTurma(turmaDTO.getAno(), turmaDTO.getCurso(), turmaDTO.getProfessor(), turmaDTO.getPeriodo());

        if (turma.isPresent()) {
            throw new ActionNotAllowedException("Já existe uma turma para esse ano e para esse curso, com esse mesmo professor nesse mesmo período!");
        }
    }

    public Turma turmaOfTurmaDTO(TurmaDTO turmaDTO) {
        Turma turma = new Turma();
        CursoDTO cursoDTO = this.cursoService.findOptional(turmaDTO.getCurso());
        ProfessorDTO professorDTO = this.professorService.findOptional(turmaDTO.getProfessor());
        List<AlunoDTO> alunoDTO = turmaDTO.getAlunos().stream().map(id -> this.alunoService.findAlunoDTO(id)).collect(Collectors.toList());
        List<Aluno> alunos = alunoDTO.stream().map(aluno -> this.alunoService.alunoOfAlunoDTO(aluno)).collect(Collectors.toList());

        turma.setId(turmaDTO.getId());
        turma.setAno(turmaDTO.getAno());
        turma.setCurso(this.cursoService.cursoOfCursoDTO(cursoDTO));
        turma.setProfessor(this.professorService.professorOfDTO(professorDTO));
        turma.setAlunos(alunos);
        turma.setPeriodo(turmaDTO.getPeriodo());
        return turma;
    }

    public TurmaDTO turmaDTOofEntity(Turma turma) {
        TurmaDTO turmaDTO = new TurmaDTO();
        turmaDTO.setId(turma.getId());
        turmaDTO.setAno(turma.getAno());
        turmaDTO.setCurso(turma.getCurso().getId());
        turmaDTO.setProfessor(turma.getProfessor().getId());
        turmaDTO.setPeriodo(turma.getPeriodo());
        return turmaDTO;
    }

    public TurmaListDTO turmaListDTOofEntity(Turma turma) {
        TurmaListDTO turmaListDTO = new TurmaListDTO();
        turmaListDTO.setId(turma.getId());
        turmaListDTO.setAno(turma.getAno());
        turmaListDTO.setCurso(turma.getCurso().getName());
        turmaListDTO.setProfessor(turma.getProfessor().getName());
        turmaListDTO.setPeriodo(turma.getPeriodo());
        return turmaListDTO;
    }

    public TurmaEditDTO turmaEditDTOofEntity(Turma turma) {
        TurmaEditDTO turmaEditDTO = new TurmaEditDTO();
        turmaEditDTO.setId(turma.getId());
        turmaEditDTO.setAno(turma.getAno());
        turmaEditDTO.setCurso(turma.getCurso());
        turmaEditDTO.setProfessor(turma.getProfessor());
        turmaEditDTO.setPeriodo(turma.getPeriodo());
        return turmaEditDTO;
    }
}
