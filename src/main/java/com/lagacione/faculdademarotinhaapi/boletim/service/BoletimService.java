package com.lagacione.faculdademarotinhaapi.boletim.service;

import com.lagacione.faculdademarotinhaapi.aluno.service.AlunoService;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimListaDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimPDFDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimToEditDTO;
import com.lagacione.faculdademarotinhaapi.commons.models.GerarPDFBoletimDTO;
import com.lagacione.faculdademarotinhaapi.boletim.repository.BoletimRepository;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.service.MateriaNotaBimestreService;
import com.lagacione.faculdademarotinhaapi.professor.service.ProfessorService;
import com.lagacione.faculdademarotinhaapi.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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

    public BoletimToEditDTO find(Integer id) throws ObjectNotFoundException {
        Optional<Boletim> boletim = this.boletimRepository.findById(id);

        if (boletim == null) {
            throw new ObjectNotFoundException("Boletim não encontrado!");
        }

        return BoletimToEditDTO.of(boletim.get());
    }

    public BoletimDTO findOptional(Integer id) throws ObjectNotFoundException {
        Optional<Boletim> boletim = this.boletimRepository.findById(id);

        if (!boletim.isPresent()) {
            throw new ObjectNotFoundException("Boletim não encontrado!");
        }

        return BoletimDTO.of(boletim.get());
    }

    private BoletimDTO insert(Boletim boletim) {
        boletim.setId(null);
        return BoletimDTO.of(this.boletimRepository.save(boletim));
    }

    private BoletimDTO update(Boletim boletim) throws ObjectNotFoundException {
        Boletim newBoletim = Boletim.of(this.findOptional(boletim.getId()));
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
        this.validarProfessor(boletimDTO);
        this.validarAluno(boletimDTO);
        this.validarCurso(boletimDTO);

        Boletim boletim = Boletim.of(boletimDTO);

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
        BoletimDTO boletimDTO = this.findOptional(materiaNotaBimestreDTO.getIdBoletim());
        List<MateriaNotaBimestreDTO> notas = boletimDTO.getNotas();
        notas.add(materiaNotaBimestreDTO);
        boletimDTO.setNotas(notas);
        this.update(Boletim.of(boletimDTO));
    }

    public void alterarNotaBoletim(MateriaNotaBimestreDTO materiaNotaBimestreDTO) {
        BoletimDTO boletimDTO = this.findOptional(materiaNotaBimestreDTO.getIdBoletim());
        List<MateriaNotaBimestreDTO> notas = findAndRemoveNota(boletimDTO.getNotas(), materiaNotaBimestreDTO.getId());
        notas.add(materiaNotaBimestreDTO);
        boletimDTO.setNotas(notas);
        this.update(Boletim.of(boletimDTO));
    }

    public void removerNotaBoletim(MateriaNotaBimestreDTO materiaNotaBimestreDTO) {
        BoletimDTO boletimDTO = this.findOptional(materiaNotaBimestreDTO.getIdBoletim());
        List<MateriaNotaBimestreDTO> notas = findAndRemoveNota(boletimDTO.getNotas(), materiaNotaBimestreDTO.getId());
        boletimDTO.setNotas(notas);
        this.update(Boletim.of(boletimDTO));
    }

    private List<MateriaNotaBimestreDTO> findAndRemoveNota(List<MateriaNotaBimestreDTO> notas, Integer idNotaRemove) {
        for (MateriaNotaBimestreDTO nota : notas) {
            if (nota.getId() == idNotaRemove) {
                notas.remove(nota);
                break;
            }
        }

        return notas;
    }

    public void gerarBoletim(Integer id, HttpServletResponse response) throws Exception {
        try {
            BoletimPDFDTO boletimPDF = BoletimPDFDTO.of(this.findOptional(id));
            GerarPDFBoletimDTO boletim = new GerarPDFBoletimDTO();
            boletim.gerarBoletim(boletimPDF, response);
        } catch (Exception e) {
            throw new Exception("Erro ao gerar boletim: " + e.getMessage());
        }

    }
}
