package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Aluno;
import com.lagacione.faculdademarotinhaapi.domain.Curso;
import com.lagacione.faculdademarotinhaapi.dto.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.repositories.AlunoRepository;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<AlunoDTO> findAll() {
        List<Aluno> alunos = this.alunoRepository.findAll();
        List<AlunoDTO> alunosDTO = alunos.stream().map(
                obj -> new AlunoDTO(obj.getName(), obj.getAge(), obj.getCpf(), obj.getPhone(), obj.getId(), obj.getCursos())
        ).collect(Collectors.toList());
        return alunosDTO;
    }

    public Page<AlunoDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<Aluno> alunos = this.alunoRepository.findAll(pageRequest);
        Page<AlunoDTO> alunosDTO = alunos.map(
                obj -> new AlunoDTO(obj.getName(), obj.getAge(), obj.getCpf(), obj.getPhone(), obj.getId(), obj.getCursos())
        );
        return alunosDTO;
    }

    public Aluno find(Integer id) throws ObjectNotFoundException {
        Optional<Aluno> aluno = this.alunoRepository.findById(id);
        return aluno.orElseThrow(() -> new ObjectNotFoundException("Aluno não encontrado!"));
    }

    private Aluno insert(Aluno professor) {
        professor.setId(null);
        return this.alunoRepository.save(professor);
    }

    private Aluno update(Aluno aluno) throws ObjectNotFoundException {
        Aluno newAluno = this.find(aluno.getId());
        this.updateData(newAluno, aluno);
        return this.alunoRepository.save(newAluno);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.alunoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover este aluno!");
        }
    }

    public Aluno fromDto(AlunoDTO alunoDTO) {
        return new Aluno(
                alunoDTO.getName(),
                alunoDTO.getAge(),
                alunoDTO.getCpf(),
                alunoDTO.getPhone(),
                alunoDTO.getId(),
                alunoDTO.getCurso()
        );
    }

    private void updateData(Aluno newProfessor, Aluno professor) {
        newProfessor.setName(professor.getName());
        newProfessor.setAge(professor.getAge());
        newProfessor.setCpf(professor.getCpf());
        newProfessor.setPhone(professor.getPhone());
        newProfessor.setId(professor.getId());
        newProfessor.setCurso(professor.getCurso());
    }

    public Aluno salvarRegistro(AlunoDTO alunoDTO, Boolean adicionar) throws Exception {
        this.validarCurso(alunoDTO);

        Aluno aluno = this.fromDto(alunoDTO);

        if (adicionar) {
            this.validarCpf(alunoDTO);
            return this.insert(aluno);
        }

        return this.update(aluno);
    }

    private void validarCurso(AlunoDTO alunoDTO) throws ObjectNotFoundException {
        List<Curso> cursos = alunoDTO.getCurso();

        if (cursos == null || cursos.size() == 0) {
            throw new ObjectNotFoundException("Por favor informe ao menos um curso!");
        }

        for (Curso curso : cursos) {
            this.cursoService.find(curso.getId());
        }
    }

    private void validarCpf(AlunoDTO alunoDTO) throws Exception {
        Integer validar = this.alunoRepository.pesquisarCpf(alunoDTO.getCpf());

        if (validar > 0) {
            throw new Exception("Já existe um aluno cadastrado com esse CPF. Por favor informe outro CPF!");
        }
    }
}
