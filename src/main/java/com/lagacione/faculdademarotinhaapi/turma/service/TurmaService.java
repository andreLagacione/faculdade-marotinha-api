package com.lagacione.faculdademarotinhaapi.turma.service;

import com.lagacione.faculdademarotinhaapi.aluno.service.AlunoService;
import com.lagacione.faculdademarotinhaapi.boletim.service.BoletimService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.professor.service.ProfessorService;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;
import com.lagacione.faculdademarotinhaapi.turma.model.*;
import com.lagacione.faculdademarotinhaapi.turma.repository.TurmaRepository;
import com.lagacione.faculdademarotinhaapi.turma.specification.TurmaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService {
    private TurmaRepository turmaRepository;
    private CursoService cursoService;
    private ProfessorService professorService;
    private BoletimService boletimService;
    private EntityManager entityManager;

    @Autowired
    public void TurmaService(
            TurmaRepository turmaRepository,
            CursoService cursoService,
            ProfessorService professorService,
            BoletimService boletimService,
            @Qualifier("entityManagerFactory") EntityManager entityManager
    ) {
        this.turmaRepository = turmaRepository;
        this.cursoService = cursoService;
        this.professorService = professorService;
        this.boletimService = boletimService;
        this.entityManager = entityManager;
    }

    public List<TurmaComboListDTO> findAll() {
        List<Turma> turmas = this.turmaRepository.findAll();
        List<TurmaComboListDTO> turmaListDTO = turmas.stream().map(turma -> this.turmaComboListDTOofEntity(turma)).collect(Collectors.toList());
        return turmaListDTO;
    }

    public Page<TurmaListDTO> findPage(Pageable pageable, TurmaFilter filter) {
        TurmaSpecification turmaSpecification = new TurmaSpecification();
        Specification<Turma> specification = turmaSpecification.buildFilter(filter);
        Page<Turma> turmaPage = this.turmaRepository.findAll(specification, pageable);
        List<TurmaListDTO> turmaListDTO = turmaPage.getContent().stream().map(turma -> this.turmaListDTOofEntity(turma)).collect(Collectors.toList());
        return new PageImpl<>(turmaListDTO, pageable, turmaPage.getTotalElements());
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
        turma.setPeriodo(turma.getPeriodo().toLowerCase());
        return this.turmaDTOofEntity(this.turmaRepository.save(turma));
    }

    public TurmaDTO findTurmaDTO(Integer id) throws ObjectNotFoundException {
        return this.turmaDTOofEntity(this.findTurma(id));
    }

    private TurmaDTO update(Turma turma) throws ObjectNotFoundException {
        turma.setPeriodo(turma.getPeriodo().toLowerCase());
        Turma newTurma = this.turmaOfTurmaDTO(this.findTurmaDTO(turma.getId()));
        this.updateData(newTurma, turma);
        return this.turmaDTOofEntity(this.turmaRepository.save(newTurma));
    }

    public void delete(Integer id) {
        Turma turma = this.findTurma(id);

        if (turma.getAlunos().size() > 0) {
            throw new ActionNotAllowedException("Essa turma não pode ser excluída pois existem alunos atrelados à ela!");
        }

        if (this.boletimService.getBoletinsByTurmaId(id).size() > 0) {
            throw new ActionNotAllowedException("Existem boletins atrelados a esse curso!");
        }

        this.turmaRepository.deleteById(id);
    }

    private void updateData(Turma newTurma, Turma turma) {
        newTurma.setAno(turma.getAno());
        newTurma.setCurso(turma.getCurso());
        newTurma.setProfessor(turma.getProfessor());
        newTurma.setAlunos(turma.getAlunos());
        newTurma.setPeriodo(turma.getPeriodo());
    }

    public TurmaDTO salvarRegistro(TurmaDTO turmaDTO, Boolean adicionar) throws Exception {
        this.validarPeriodo(turmaDTO.getPeriodo().toLowerCase());
        this.validarTurma(turmaDTO);

        Turma turma = this.turmaOfTurmaDTO(turmaDTO);

        if (adicionar) {
            return this.insert(turma);
        }

        return this.update(turma);
    }

    private void validarPeriodo(String periodo) throws Exception {
        if (!"manhã".equals(periodo) && !"tarde".equals(periodo) && !"noite".equals(periodo)) {
            throw new Exception("O período informado é inválido!");
        }
    }

    private void validarTurma(TurmaDTO turmaDTO) {
        Optional<Turma> turma = this.turmaRepository.validarTurma(turmaDTO.getAno(), turmaDTO.getCurso(), turmaDTO.getProfessor(), turmaDTO.getPeriodo());

        if (turma.isPresent() && turma.get().getId() != turmaDTO.getId()) {
            throw new ActionNotAllowedException("Já existe uma turma para esse ano e para esse curso, com esse mesmo professor nesse mesmo período!");
        }
    }

    public List<TurmaDTO> findTurmaByCursoId(Integer idCurso) {
        List<Turma> turmas = this.turmaRepository.findTurmaByCursoId(idCurso);
        return turmas.stream().map(turma -> this.turmaDTOofEntity(turma)).collect(Collectors.toList());
    }

    public List<TurmaDTO> findTurmaByProfessorId(Integer idProfessor) {
        List<Turma> turmas = this.turmaRepository.findTurmaByProfessorId(idProfessor);
        return turmas.stream().map(turma -> this.turmaDTOofEntity(turma)).collect(Collectors.toList());
    }

    public Turma turmaOfTurmaDTO(TurmaDTO turmaDTO) {
        Turma turma = new Turma();
        CursoDTO cursoDTO = this.cursoService.findOptional(turmaDTO.getCurso());
        ProfessorDTO professorDTO = this.professorService.findOptional(turmaDTO.getProfessor());

        turma.setId(turmaDTO.getId());
        turma.setAno(turmaDTO.getAno());
        turma.setCurso(this.cursoService.cursoOfCursoDTO(cursoDTO));
        turma.setProfessor(this.professorService.professorOfDTO(professorDTO));
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
        turmaListDTO.setTotalAlunos(turma.getAlunos().size());
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

    public TurmaComboListDTO turmaComboListDTOofEntity(Turma turma) {
        TurmaComboListDTO turmaComboListDTO = new TurmaComboListDTO();
        turmaComboListDTO.setId(turma.getId());
        turmaComboListDTO.setName(turma.getCurso().getName() + " - período da " + turma.getPeriodo());
        return turmaComboListDTO;
    }

}
