package com.lagacione.faculdademarotinhaapi.aluno.service;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoForEditDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoListaDTO;
import com.lagacione.faculdademarotinhaapi.aluno.repository.AlunoRepository;
import com.lagacione.faculdademarotinhaapi.boletim.service.BoletimService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.turma.entity.Turma;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaComboListDTO;
import com.lagacione.faculdademarotinhaapi.turma.model.TurmaDTO;
import com.lagacione.faculdademarotinhaapi.turma.service.TurmaService;
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
public class AlunoService {
    private AlunoRepository alunoRepository;
    private CursoService cursoService;
    private TurmaService turmaService;
    private BoletimService boletimService;

    @Autowired
    public void AlunoService(AlunoRepository alunoRepository, CursoService cursoService, TurmaService turmaService, BoletimService boletimService) {
        this.alunoRepository = alunoRepository;
        this.cursoService = cursoService;
        this.turmaService = turmaService;
        this.boletimService = boletimService;
    }

    public List<AlunoListaDTO> findAll() {
        List<Aluno> alunos = this.alunoRepository.findAll();
        List<AlunoListaDTO> alunoListaDTO = alunos.stream().map(aluno -> this.alunoListaDTOofEntity(aluno)).collect(Collectors.toList());
        return alunoListaDTO;
    }

    public Page<AlunoListaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Aluno> alunos = this.alunoRepository.findAll(pageRequest);
        Page<AlunoListaDTO> alunoListaDTO = alunos.map(aluno -> this.alunoListaDTOofEntity(aluno));
        return alunoListaDTO;
    }

    private Aluno findAluno(Integer id) throws ObjectNotFoundException {
        Optional<Aluno> aluno = this.alunoRepository.findById(id);

        if (!aluno.isPresent()) {
            throw new ObjectNotFoundException("Aluno não encontrado!");
        }

        return aluno.get();
    }

    public AlunoForEditDTO find(Integer id) throws ObjectNotFoundException {
        return this.alunoCursoListaDTOofAluno(this.findAluno(id));
    }

    private AlunoDTO insert(Aluno aluno) {
        aluno.setId(null);
        return this.alunoDTOofAluno(this.alunoRepository.save(aluno));
    }

    public AlunoDTO findAlunoDTO(Integer id) throws ObjectNotFoundException {
        return this.alunoDTOofAluno(this.findAluno(id));
    }

    private AlunoDTO update(Aluno aluno) throws ObjectNotFoundException {
        Aluno newAluno = this.alunoOfAlunoDTO(this.findAlunoDTO(aluno.getId()));
        this.updateData(newAluno, aluno);
        return this.alunoDTOofAluno(this.alunoRepository.save(newAluno));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.boletimService.deleteBoletimByAlunoId(id);
            this.alunoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover este aluno!");
        }
    }

    private void updateData(Aluno newAluno, Aluno aluno) {
        newAluno.setName(aluno.getName());
        newAluno.setAge(aluno.getAge());
        newAluno.setCpf(aluno.getCpf());
        newAluno.setPhone(aluno.getPhone());
        newAluno.setId(aluno.getId());
        newAluno.setTurmas(aluno.getTurmas());
    }

    public AlunoDTO salvarRegistro(AlunoDTO alunoDTO, Boolean adicionar) throws ActionNotAllowedException {
        this.validarTurma(alunoDTO);
        this.validarCpf(alunoDTO);
        Aluno aluno = this.alunoOfAlunoDTO(alunoDTO);

        if (adicionar) {
            return this.insert(aluno);
        }

        return this.update(aluno);
    }

    private void validarTurma(AlunoDTO alunoDTO) throws ObjectNotFoundException {
        List<Integer> turmas = alunoDTO.getTurmas();

        if (turmas == null || turmas.size() == 0) {
            throw new ObjectNotFoundException("Por favor informe ao menos uma turma!");
        }

        for (Integer idTurma : turmas) {
            this.turmaService.find(idTurma);
        }
    }

    private void validarCpf(AlunoDTO alunoDTO) throws ActionNotAllowedException {
        Optional<Aluno> aluno = this.alunoRepository.pesquisarCpf(alunoDTO.getCpf());

        if (aluno.isPresent() && aluno.get().getId() != alunoDTO.getId()) {
            throw new ActionNotAllowedException("Já existe um aluno cadastrado com esse CPF. Por favor informe outro CPF!");
        }
    }

    public Aluno alunoOfAlunoDTO(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        List<TurmaDTO> turmasDTO = alunoDTO.getTurmas().stream().map(idTurma -> this.turmaService.findTurmaDTO(idTurma)).collect(Collectors.toList());
        List<Turma> turmas = turmasDTO.stream().map(turmaDTO -> this.turmaService.turmaOfTurmaDTO(turmaDTO)).collect(Collectors.toList());

        aluno.setId(alunoDTO.getId());
        aluno.setName(alunoDTO.getName());
        aluno.setAge(alunoDTO.getAge());
        aluno.setCpf(alunoDTO.getCpf());
        aluno.setPhone(alunoDTO.getPhone());
        aluno.setTurmas(turmas);
        return aluno;
    }

    public AlunoForEditDTO alunoCursoListaDTOofAluno(Aluno aluno) {
        AlunoForEditDTO alunoForEditDTO = new AlunoForEditDTO();
        List<TurmaComboListDTO> turmas = aluno.getTurmas().stream().map(turma -> this.turmaService.turmaComboListDTOofEntity(turma)).collect(Collectors.toList());

        alunoForEditDTO.setId(aluno.getId());
        alunoForEditDTO.setName(aluno.getName());
        alunoForEditDTO.setCpf(aluno.getCpf());
        alunoForEditDTO.setAge(aluno.getAge());
        alunoForEditDTO.setPhone(aluno.getPhone());
        alunoForEditDTO.setTurmas(turmas);
        return alunoForEditDTO;
    }

    public AlunoDTO alunoDTOofAluno(Aluno aluno) {
        AlunoDTO alunoDTO = new AlunoDTO();
        List<Integer> turmas = aluno.getTurmas().stream().map(turma -> turma.getId()).collect(Collectors.toList());

        alunoDTO.setId(aluno.getId());
        alunoDTO.setName(aluno.getName());
        alunoDTO.setAge(aluno.getAge());
        alunoDTO.setCpf(aluno.getCpf());
        alunoDTO.setPhone(aluno.getPhone());
        alunoDTO.setTurmas(turmas);
        return alunoDTO;
    }

    public AlunoListaDTO alunoListaDTOofEntity(Aluno aluno) {
        AlunoListaDTO alunoListaDTO = new AlunoListaDTO();
        alunoListaDTO.setId(aluno.getId());
        alunoListaDTO.setName(aluno.getName());
        alunoListaDTO.setCpf(aluno.getCpf());
        alunoListaDTO.setAge(aluno.getAge());
        alunoListaDTO.setPhone(aluno.getPhone());
        return alunoListaDTO;
    }
}
