package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Curso;
import com.lagacione.faculdademarotinhaapi.domain.MateriaNota;
import com.lagacione.faculdademarotinhaapi.dto.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.dto.BimestreDTO;
import com.lagacione.faculdademarotinhaapi.dto.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaDTO;
import com.lagacione.faculdademarotinhaapi.repositories.MateriaNotaRepository;
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

    public Page<MateriaNotaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
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
        this.verificarSeNotaJaExisteParaAlunoBimestre(materiaNotaDTO);

        MateriaNota materiaNota = MateriaNota.of(materiaNotaDTO);

        if (adicionar) {
            return this.insert(materiaNota);
        }

        return this.update(materiaNota);
    }

    private void validarAluno(MateriaNotaDTO materiaNotaDTO) {
        AlunoDTO aluno = materiaNotaDTO.getAluno();

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
        MateriaDTO materia = materiaNotaDTO.getMateria();

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

    private void verificarSeNotaJaExisteParaAlunoBimestre(MateriaNotaDTO materiaNotaDTO) throws Exception {
        Integer idAluno = materiaNotaDTO.getAluno().getId();
        Integer idBimestre = materiaNotaDTO.getBimestre().getId();
        Integer idCurso = materiaNotaDTO.getCurso().getId();
        Integer idMateria = materiaNotaDTO.getMateria().getId();

        List<MateriaNota> notas = this.materiaNotaRepository.getNotasByAluno(idAluno);

        for (MateriaNota nota : notas) {
            Integer notaIdAluno = nota.getAluno().getId();
            Integer notaIdBimestre = nota.getBimestre().getId();
            Integer notaIdCurso = nota.getCurso().getId();
            Integer notaIdMateria = nota.getMateria().getId();

            if (
                    notaIdAluno == idAluno &&
                    notaIdBimestre == idBimestre &&
                    notaIdCurso == idCurso &&
                    notaIdMateria == idMateria
            ) {
                throw new Exception("Já existe uma nota cadastrada para este aluno, nesta mesma matéria, neste mesmo curso e para o mesmo bimestre.");
            }
        }
    }

    public List<MateriaNota> getNotaByAlunoAndCurso(Integer idAluno, Integer idCurso, Integer ano) {
        return this.materiaNotaRepository.getNotaByAlunoAndCurso(idAluno, idCurso, ano);
    }
}
