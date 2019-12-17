package com.lagacione.faculdademarotinhaapi.aluno.service;

import com.lagacione.faculdademarotinhaapi.aluno.entity.Aluno;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoCursoListaDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoListaDTO;
import com.lagacione.faculdademarotinhaapi.aluno.repository.AlunoRepository;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoNomeListaDTO;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
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

    @Autowired
    public void AlunoService(AlunoRepository alunoRepository, CursoService cursoService) {
        this.alunoRepository = alunoRepository;
        this.cursoService = cursoService;
    }

    public List<AlunoListaDTO> findAll() {
        List<Aluno> alunos = this.alunoRepository.findAll();
        List<AlunoListaDTO> alunoListaDTO = alunos.stream().map(AlunoListaDTO::of).collect(Collectors.toList());
        return alunoListaDTO;
    }

    public Page<AlunoListaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Aluno> alunos = this.alunoRepository.findAll(pageRequest);
        Page<AlunoListaDTO> alunoListaDTO = alunos.map(AlunoListaDTO::of);
        return alunoListaDTO;
    }

    private Aluno findAluno(Integer id) throws ObjectNotFoundException {
        Optional<Aluno> aluno = this.alunoRepository.findById(id);

        if (!aluno.isPresent()) {
            throw new ObjectNotFoundException("Aluno não encontrado!");
        }

        return aluno.get();
    }

    public AlunoCursoListaDTO find(Integer id) throws ObjectNotFoundException {
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
        newAluno.setCursos(aluno.getCursos());
    }

    public AlunoDTO salvarRegistro(AlunoDTO alunoDTO, Boolean adicionar) throws ActionNotAllowedException {
        this.validarCurso(alunoDTO);

        Aluno aluno = this.alunoOfAlunoDTO(alunoDTO);

        if (adicionar) {
            this.validarCpf(alunoDTO);
            return this.insert(aluno);
        }

        return this.update(aluno);
    }

    private void validarCurso(AlunoDTO alunoDTO) throws ObjectNotFoundException {
        List<Integer> cursos = alunoDTO.getCursos();

        if (cursos == null || cursos.size() == 0) {
            throw new ObjectNotFoundException("Por favor informe ao menos um curso!");
        }

        for (Integer idCurso : cursos) {
            this.cursoService.find(idCurso);
        }
    }

    private void validarCpf(AlunoDTO alunoDTO) throws ActionNotAllowedException {
        Optional<Aluno> aluno = this.alunoRepository.pesquisarCpf(alunoDTO.getCpf());

        if (aluno.isPresent()) {
            throw new ActionNotAllowedException("Já existe um aluno cadastrado com esse CPF. Por favor informe outro CPF!");
        }
    }

    public Aluno alunoOfAlunoDTO(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        aluno.setId(alunoDTO.getId());
        aluno.setName(alunoDTO.getName());
        aluno.setAge(alunoDTO.getAge());
        aluno.setCpf(alunoDTO.getCpf());
        aluno.setPhone(alunoDTO.getPhone());

        List<CursoDTO> cursosDTO = alunoDTO.getCursos().stream().map(id -> this.cursoService.findOptional(id)).collect(Collectors.toList());
        List<Curso> cursos = cursosDTO.stream().map(curso -> this.cursoService.cursoOfCursoDTO(curso)).collect(Collectors.toList());

        aluno.setCursos(cursos);
        return aluno;
    }

    public AlunoCursoListaDTO alunoCursoListaDTOofAluno(Aluno aluno) {
        AlunoCursoListaDTO alunoCursoListaDTO = new AlunoCursoListaDTO();
        alunoCursoListaDTO.setId(aluno.getId());
        alunoCursoListaDTO.setName(aluno.getName());
        alunoCursoListaDTO.setCpf(aluno.getCpf());
        alunoCursoListaDTO.setAge(aluno.getAge());
        alunoCursoListaDTO.setPhone(aluno.getPhone());
        List<CursoNomeListaDTO> cursos = aluno.getCursos().stream().map(curso -> this.cursoService.cursoNomeListaDTOofCurso(curso)).collect(Collectors.toList());
        alunoCursoListaDTO.setCursos(cursos);
        return alunoCursoListaDTO;
    }

    public AlunoDTO alunoDTOofAluno(Aluno aluno) {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(aluno.getId());
        alunoDTO.setName(aluno.getName());
        alunoDTO.setAge(aluno.getAge());
        alunoDTO.setCpf(aluno.getCpf());
        alunoDTO.setPhone(aluno.getPhone());


        List<Integer> cursosDTO = aluno.getCursos().stream().map(curso -> curso.getId()).collect(Collectors.toList());


        alunoDTO.setCursos(cursosDTO);
        return alunoDTO;
    }
}
