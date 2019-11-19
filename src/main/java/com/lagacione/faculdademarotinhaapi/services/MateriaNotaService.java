package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.*;
import com.lagacione.faculdademarotinhaapi.dto.BimestreDTO;
import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaDTO;
import com.lagacione.faculdademarotinhaapi.repositories.MateriaNotaRepository;
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
public class MateriaNotaService {
    @Autowired
    private MateriaNotaRepository materiaNotaRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private BimestreService bimestreService;

    @Autowired
    private MateriaService materiaService;

    public List<MateriaNotaDTO> findAll() {
        List<MateriaNota> materiaNotas = this.materiaNotaRepository.findAll();
        List<MateriaNotaDTO> materiaNotasDTO = materiaNotas.stream().map(MateriaNotaDTO::of).collect(Collectors.toList());
        return materiaNotasDTO;
    }

    public Page<MateriaNotaDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<MateriaNota> materiaNotas = this.materiaNotaRepository.findAll(pageRequest);
        Page<MateriaNotaDTO> materiaNotasDTO = materiaNotas.map(MateriaNotaDTO::of);
        return materiaNotasDTO;
    }

    public MateriaNota find(Integer id) throws ObjectNotFoundException {
        Optional<MateriaNota> materiaNota = this.materiaNotaRepository.findById(id);
        return materiaNota.orElseThrow(() -> new ObjectNotFoundException("Nota não encontrada!"));
    }

    private MateriaNota insert(MateriaNota materiaNota) {
        materiaNota.setId(null);
        return this.materiaNotaRepository.save(materiaNota);
    }

    private MateriaNota update(MateriaNota materiaNota) throws ObjectNotFoundException {
        MateriaNota newMateriaNota = this.find(materiaNota.getId());
        this.updateData(newMateriaNota, materiaNota);
        return this.materiaNotaRepository.save(newMateriaNota);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.materiaNotaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover esta nota!");
        }
    }

    private void updateData(MateriaNota newMateriaNota, MateriaNota materiaNota) {
        newMateriaNota.setId(materiaNota.getId());
        newMateriaNota.setAluno(materiaNota.getAluno());
        newMateriaNota.setCurso(materiaNota.getCurso());
        newMateriaNota.setBimestre(materiaNota.getBimestre());
        newMateriaNota.setMateria(materiaNota.getMateria());
        newMateriaNota.setNota(materiaNota.getNota());
    }

    public MateriaNota salvarRegistro(MateriaNotaDTO materiaNotaDTO, Boolean adicionar) throws Exception {
        this.validarAluno(materiaNotaDTO);
        this.validarCurso(materiaNotaDTO);
        this.validarBimestre(materiaNotaDTO);
        this.validarMateria(materiaNotaDTO);
        this.validarNota(materiaNotaDTO);

        MateriaNota materiaNota = MateriaNota.of(materiaNotaDTO);

        if (adicionar) {
            return this.insert(materiaNota);
        }

        return this.update(materiaNota);
    }

    private void validarAluno(MateriaNotaDTO materiaNotaDTO) {
        Aluno aluno = materiaNotaDTO.getAluno();

        if (aluno == null) {
            throw new ObjectNotFoundException("O aluno informado não foi encontrado. Informe um aluno válido!");
        }

        this.alunoService.find(aluno.getId());
    }

    private void validarCurso(MateriaNotaDTO materiaNotaDTO) {
        Curso curso = materiaNotaDTO.getCurso();

        if (curso == null) {
            throw new ObjectNotFoundException("O curso informado não foi encontrado. Informe um curso válido!");
        }

        this.cursoService.find(curso.getId());
    }

    private void validarBimestre(MateriaNotaDTO materiaNotaDTO) {
        BimestreDTO bimestre = materiaNotaDTO.getBimestre();

        if (bimestre == null) {
            throw new ObjectNotFoundException("O bimestre informado não foi encontrado. Informe um bimestre válido!");
        }

        this.bimestreService.find(bimestre.getId());
    }

    private void validarMateria(MateriaNotaDTO materiaNotaDTO) {
        Materia materia = materiaNotaDTO.getMateria();

        if (materia == null) {
            throw new ObjectNotFoundException("A matéria informada não foi encontrada. Informe uma matéria válida!");
        }

        this.materiaService.find(materia.getId());
    }

    private void validarNota(MateriaNotaDTO materiaNotaDTO) throws Exception {
        Double nota = materiaNotaDTO.getNota();

        if (nota < 0 && nota > 10) {
            throw new Exception("A nota deve estar entre 0 e 10. Por favor informe uma nota válida!");
        }
    }
}
