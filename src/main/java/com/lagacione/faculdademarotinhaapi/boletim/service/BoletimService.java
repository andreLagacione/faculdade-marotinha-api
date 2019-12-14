package com.lagacione.faculdademarotinhaapi.boletim.service;

import com.lagacione.faculdademarotinhaapi.aluno.service.AlunoService;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimListaDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimPDFDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimToEditDTO;
import com.lagacione.faculdademarotinhaapi.commons.models.GerarPDFBoletimDTO;
import com.lagacione.faculdademarotinhaapi.boletim.repository.BoletimRepository;
import com.lagacione.faculdademarotinhaapi.curso.entity.Curso;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.entity.MateriaNotaBimestre;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestrePDFDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.service.MateriaNotaBimestreService;
import com.lagacione.faculdademarotinhaapi.professor.service.ProfessorService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoletimService {
    private BoletimRepository boletimRepository;
    private ProfessorService professorService;
    private AlunoService alunoService;
    private CursoService cursoService;
    private MateriaNotaBimestreService materiaNotaBimestreService;

    @Autowired
    public void BoletimService(BoletimRepository boletimRepository, ProfessorService professorService, AlunoService alunoService, CursoService cursoService, MateriaNotaBimestreService materiaNotaBimestreService) {
        this.boletimRepository = boletimRepository;
        this.professorService = professorService;
        this.alunoService = alunoService;
        this.cursoService = cursoService;
        this.materiaNotaBimestreService = materiaNotaBimestreService;
    }

    public List<BoletimListaDTO> findAll() {
        List<Boletim> boletins = this.boletimRepository.findAll();
        List<BoletimListaDTO> boletimLista = boletins.stream().map(BoletimListaDTO::of).collect(Collectors.toList());
        return boletimLista;
    }

    public Page<BoletimListaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Boletim> boletins = this.boletimRepository.findAll(pageRequest);
        Page<BoletimListaDTO> boletimLista = boletins.map(BoletimListaDTO::of);
        return boletimLista;
    }

    private Boletim getBoletim(Integer id) throws ObjectNotFoundException {
        Optional<Boletim> boletim = this.boletimRepository.findById(id);

        if (!boletim.isPresent()) {
            throw new ObjectNotFoundException("Boletim não encontrado!");
        }

        return boletim.get();
    }

    public BoletimToEditDTO find(Integer id) throws ObjectNotFoundException {
        return BoletimToEditDTO.of(this.getBoletim(id));
    }

    public BoletimDTO findBoletimDTO(Integer id) throws ObjectNotFoundException {
        return BoletimDTO.of(this.getBoletim(id));
    }

    private BoletimDTO insert(Boletim boletim) {
        boletim.setId(null);
        return BoletimDTO.of(this.boletimRepository.save(boletim));
    }

    private BoletimDTO update(Boletim boletim) throws ObjectNotFoundException {
        Boletim newBoletim = Boletim.of(this.findBoletimDTO(boletim.getId()), boletim.getAluno().getCursos(), boletim.getNotas(), boletim.getCurso());
        this.updateData(newBoletim, boletim);
        return BoletimDTO.of(this.boletimRepository.save(newBoletim));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.boletimRepository.deleteById(id);
            this.materiaNotaBimestreService.removerNotasBoletim(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover este boletim!");
        }
    }

    private void updateData(Boletim newBoletim, Boletim boletim) {
        newBoletim.setId(boletim.getId());
        newBoletim.setAno(boletim.getAno());
        newBoletim.setAluno(boletim.getAluno());
        newBoletim.setProfessor(boletim.getProfessor());
        newBoletim.setCurso(boletim.getCurso());
        newBoletim.setNotas(boletim.getNotas());
    }

    public BoletimDTO salvarRegistro(BoletimDTO boletimDTO, Boolean adicionar) throws Exception {
        List<Curso> cursos = this.alunoService.obterCursosById(boletimDTO.getAluno().getCursos());
        this.validarProfessor(boletimDTO);
        this.validarAluno(boletimDTO);
        this.validarCurso(boletimDTO);

        Boletim boletim = Boletim.of(
                boletimDTO,
                cursos,
                this.obterNotasById(boletimDTO.getNotas()),
                Curso.of(boletimDTO.getCurso(), this.cursoService.getMateriasById(boletimDTO.getCurso().getMaterias()))
        );

        if (adicionar) {
            return this.insert(boletim);
        }

        return this.update(boletim);
    }

    private void validarProfessor(BoletimDTO boletimDTO) {
        this.professorService.find(boletimDTO.getProfessor().getId());
    }

    private void validarAluno(BoletimDTO boletimDTO) {
        this.alunoService.find(boletimDTO.getAluno().getId());
    }

    private void validarCurso(BoletimDTO boletimDTO) {
        this.cursoService.find(boletimDTO.getCurso().getId());
    }

    public void adicionarNotaBoletim(MateriaNotaBimestreDTO materiaNotaBimestreDTO) {
        BoletimDTO boletimDTO = this.findBoletimDTO(materiaNotaBimestreDTO.getIdBoletim());
        List<Curso> cursos = this.alunoService.obterCursosById(boletimDTO.getAluno().getCursos());
        List<Integer> notas = boletimDTO.getNotas();
        notas.add(materiaNotaBimestreDTO.getId());
        boletimDTO.setNotas(notas);
        this.update(
                Boletim.of(
                        boletimDTO,
                        cursos,
                        this.obterNotasById(notas),
                        Curso.of(boletimDTO.getCurso(), this.cursoService.getMateriasById(boletimDTO.getCurso().getMaterias()))
                )
        );
    }

    public void alterarNotaBoletim(MateriaNotaBimestreDTO materiaNotaBimestreDTO) {
        BoletimDTO boletimDTO = this.findBoletimDTO(materiaNotaBimestreDTO.getIdBoletim());
        List<Curso> cursos = this.alunoService.obterCursosById(boletimDTO.getAluno().getCursos());
        List<Integer> notas = this.findAndRemoveNota(boletimDTO.getNotas(), materiaNotaBimestreDTO.getId());
        notas.add(materiaNotaBimestreDTO.getId());
        boletimDTO.setNotas(notas);
        this.update(
                Boletim.of(
                        boletimDTO,
                        cursos,
                        this.obterNotasById(notas),
                        Curso.of(boletimDTO.getCurso(), this.cursoService.getMateriasById(boletimDTO.getCurso().getMaterias()))
                )
        );
    }

    public void removerNotaBoletim(MateriaNotaBimestreDTO materiaNotaBimestreDTO) {
        BoletimDTO boletimDTO = this.findBoletimDTO(materiaNotaBimestreDTO.getIdBoletim());
        List<Curso> cursos = this.alunoService.obterCursosById(boletimDTO.getAluno().getCursos());
        List<Integer> notas = this.findAndRemoveNota(boletimDTO.getNotas(), materiaNotaBimestreDTO.getId());
        boletimDTO.setNotas(notas);
        this.update(
                Boletim.of(
                        boletimDTO,
                        cursos,
                        this.obterNotasById(notas),
                        Curso.of(boletimDTO.getCurso(), this.cursoService.getMateriasById(boletimDTO.getCurso().getMaterias()))
                )
        );
    }

    private List<Integer> findAndRemoveNota(List<Integer> notasId, Integer idNotaRemove) {
        for (Integer nota : notasId) {
            if (nota == idNotaRemove) {
                notasId.remove(nota);
                break;
            }
        }

        return notasId;
    }

    public void gerarBoletim(Integer id, HttpServletResponse response) throws Exception {
        try {
            BoletimDTO boletimDTO = this.findBoletimDTO(id);
            List<MateriaNotaBimestrePDFDTO> notas = this.obterNotasByIdForPDF(boletimDTO.getNotas());

            BoletimPDFDTO boletimPDF = BoletimPDFDTO.of(boletimDTO, notas);
            GerarPDFBoletimDTO boletim = new GerarPDFBoletimDTO();
            boletim.gerarBoletim(boletimPDF, response);
        } catch (Exception e) {
            throw new Exception("Erro ao gerar boletim: " + e.getMessage());
        }

    }

    private List<MateriaNotaBimestrePDFDTO> obterNotasByIdForPDF(List<Integer> notasId) {
        List<MateriaNotaBimestrePDFDTO> notas = new ArrayList<>();

        for (Integer id : notasId) {
            MateriaNotaBimestreDTO nota = this.materiaNotaBimestreService.find(id);
            notas.add(MateriaNotaBimestrePDFDTO.of(nota));
        }

        return notas;
    }

    private List<MateriaNotaBimestre> obterNotasById(List<Integer> notasId) {
        List<MateriaNotaBimestre> notas = new ArrayList<>();

        for (Integer id : notasId) {
            MateriaNotaBimestreDTO nota = this.materiaNotaBimestreService.find(id);
            notas.add(MateriaNotaBimestre.of(nota));
        }

        return notas;
    }
}
