package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Aluno;
import com.lagacione.faculdademarotinhaapi.dto.AlunoCursoListaDTO;
import com.lagacione.faculdademarotinhaapi.dto.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.dto.AlunoListaDTO;
import com.lagacione.faculdademarotinhaapi.dto.CursoDTO;
import com.lagacione.faculdademarotinhaapi.repositories.AlunoRepository;
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
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoService cursoService;

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

    public AlunoCursoListaDTO find(Integer id) throws ObjectNotFoundException {
        Optional<Aluno> aluno = this.alunoRepository.findById(id);

        if (aluno != null) {
            AlunoCursoListaDTO alunoCursoListaDTO = new AlunoCursoListaDTO();
            alunoCursoListaDTO = alunoCursoListaDTO.of(aluno.get());
            return alunoCursoListaDTO;
        }

        throw new ObjectNotFoundException("Aluno não encontrado!");
    }

    private AlunoDTO insert(Aluno aluno) {
        aluno.setId(null);
        return AlunoDTO.of(this.alunoRepository.save(aluno));
    }

    public AlunoDTO findOptional(Integer id) throws ObjectNotFoundException {
        Optional<Aluno> aluno = this.alunoRepository.findById(id);

        if (!aluno.isPresent()) {
            throw new ObjectNotFoundException("Aluno não encontrado!");
        }

        return AlunoDTO.of(aluno.get());
    }

    private AlunoDTO update(Aluno aluno) throws ObjectNotFoundException {
        Aluno newAluno = Aluno.of(this.findOptional(aluno.getId()));
        this.updateData(newAluno, aluno);
        return AlunoDTO.of(this.alunoRepository.save(newAluno));
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

    public AlunoDTO salvarRegistro(AlunoDTO alunoDTO, Boolean adicionar) throws Exception {
        this.validarCurso(alunoDTO);

        Aluno aluno = Aluno.of(alunoDTO);

        if (adicionar) {
            this.validarCpf(alunoDTO);
            return this.insert(aluno);
        }

        return this.update(aluno);
    }

    private void validarCurso(AlunoDTO alunoDTO) throws ObjectNotFoundException {
        List<CursoDTO> cursos = alunoDTO.getCursos();

        if (cursos == null || cursos.size() == 0) {
            throw new ObjectNotFoundException("Por favor informe ao menos um curso!");
        }

        for (CursoDTO cursoDTO : cursos) {
            this.cursoService.find(cursoDTO.getId());
        }
    }

    private void validarCpf(AlunoDTO alunoDTO) throws Exception {
        Integer validar = this.alunoRepository.pesquisarCpf(alunoDTO.getCpf());

        if (validar > 0) {
            throw new Exception("Já existe um aluno cadastrado com esse CPF. Por favor informe outro CPF!");
        }
    }
}
