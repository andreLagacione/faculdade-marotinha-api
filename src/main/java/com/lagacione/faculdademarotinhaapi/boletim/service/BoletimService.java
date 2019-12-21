package com.lagacione.faculdademarotinhaapi.boletim.service;

import com.lagacione.faculdademarotinhaapi.aluno.model.AlunoDTO;
import com.lagacione.faculdademarotinhaapi.aluno.service.AlunoService;
import com.lagacione.faculdademarotinhaapi.boletim.entity.Boletim;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimListaDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimPDFDTO;
import com.lagacione.faculdademarotinhaapi.boletim.model.BoletimToEditDTO;
import com.lagacione.faculdademarotinhaapi.boletim.repository.BoletimRepository;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ActionNotAllowedException;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.commons.models.GerarPDFBoletimDTO;
import com.lagacione.faculdademarotinhaapi.curso.model.CursoDTO;
import com.lagacione.faculdademarotinhaapi.curso.service.CursoService;
import com.lagacione.faculdademarotinhaapi.nota.entity.Nota;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaDTO;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaListDTO;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaPDFDTO;
import com.lagacione.faculdademarotinhaapi.nota.service.NotaService;
import com.lagacione.faculdademarotinhaapi.professor.model.ProfessorDTO;
import com.lagacione.faculdademarotinhaapi.professor.service.ProfessorService;
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
    private NotaService notaService;

    @Autowired
    public void BoletimService(BoletimRepository boletimRepository, ProfessorService professorService, AlunoService alunoService, CursoService cursoService, NotaService notaService) {
        this.boletimRepository = boletimRepository;
        this.professorService = professorService;
        this.alunoService = alunoService;
        this.cursoService = cursoService;
        this.notaService = notaService;
    }

    public List<BoletimListaDTO> findAll() {
        List<Boletim> boletins = this.boletimRepository.findAll();
        List<BoletimListaDTO> boletimLista = boletins.stream().map(boletim -> this.boletimListaDTOofBoletim(boletim)).collect(Collectors.toList());
        return boletimLista;
    }

    public Page<BoletimListaDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Boletim> boletins = this.boletimRepository.findAll(pageRequest);
        Page<BoletimListaDTO> boletimLista = boletins.map(boletim -> this.boletimListaDTOofBoletim(boletim));
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
        return this.boletimToEditDTOofBoletim(this.getBoletim(id));
    }

    public BoletimDTO findBoletimDTO(Integer id) throws ObjectNotFoundException {
        return this.boletimDTOofBoletim(this.getBoletim(id));
    }

    private BoletimDTO insert(Boletim boletim) {
        boletim.setId(null);
        return this.boletimDTOofBoletim(this.boletimRepository.save(boletim));
    }

    private BoletimDTO update(Boletim boletim) throws ObjectNotFoundException {
        Boletim newBoletim = this.boletimOfBoletimDTO(this.findBoletimDTO(boletim.getId()));
        this.updateData(newBoletim, boletim);
        return this.boletimDTOofBoletim(this.boletimRepository.save(newBoletim));
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.notaService.removerNotasBoletim(id);
            this.boletimRepository.deleteById(id);
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
        this.validarSeBoletimJaExiste(boletimDTO);

        Boletim boletim = this.boletimOfBoletimDTO(boletimDTO);

        if (adicionar) {
            return this.insert(boletim);
        }

        return this.update(boletim);
    }

    private void validarProfessor(BoletimDTO boletimDTO) {
        this.professorService.find(boletimDTO.getIdProfessor());
    }

    private void validarAluno(BoletimDTO boletimDTO) {
        this.alunoService.find(boletimDTO.getIdAluno());
    }

    private void validarCurso(BoletimDTO boletimDTO) {
        this.cursoService.find(boletimDTO.getIdCurso());
    }

    private void validarSeBoletimJaExiste(BoletimDTO boletimDTO) {
        Optional<Boletim> boletim = this.boletimRepository.validarSeBoletimJaExiste(boletimDTO.getAno(), boletimDTO.getIdProfessor(), boletimDTO.getIdAluno(), boletimDTO.getIdCurso());

        if (boletim.isPresent() && boletim.get().getId() != boletimDTO.getId()) {
            throw new ActionNotAllowedException("Já existe um boletim cadastro para esse ano, com esse professor, para esse aluno e para esse curso!");
        }
    }

    public void adicionarNotaBoletim(NotaDTO notaDTO) {
        BoletimDTO boletimDTO = this.findBoletimDTO(notaDTO.getIdBoletim());
        List<Integer> notas = boletimDTO.getNotas();
        notas.add(notaDTO.getId());
        boletimDTO.setNotas(notas);
        this.update(this.boletimOfBoletimDTO(boletimDTO));
    }

    public void alterarNotaBoletim(NotaDTO notaDTO) {
        BoletimDTO boletimDTO = this.findBoletimDTO(notaDTO.getIdBoletim());
        List<Integer> notas = this.findAndRemoveNota(boletimDTO.getNotas(), notaDTO.getId());
        notas.add(notaDTO.getId());
        boletimDTO.setNotas(notas);
        this.update(this.boletimOfBoletimDTO(boletimDTO));
    }

    public void removerNotaBoletim(NotaDTO notaDTO) {
        BoletimDTO boletimDTO = this.findBoletimDTO(notaDTO.getIdBoletim());
        List<Integer> notas = this.findAndRemoveNota(boletimDTO.getNotas(), notaDTO.getId());
        boletimDTO.setNotas(notas);
        this.update(this.boletimOfBoletimDTO(boletimDTO));
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
            BoletimPDFDTO boletimPDF = this.boletimPDFDTOofBoletimDTO(boletimDTO);
            GerarPDFBoletimDTO boletim = new GerarPDFBoletimDTO();
            boletim.gerarBoletim(boletimPDF, response);
        } catch (Exception e) {
            throw new Exception("Erro ao gerar boletim: " + e.getMessage());
        }

    }

    public void deleteBoletimByAlunoId(Integer idAluno) {
        List<Boletim> boletins = this.boletimRepository.getBoletinsByAlunoId(idAluno);

        for (Boletim boletim : boletins) {
            this.delete(boletim.getId());
        }
    }

    public List<BoletimDTO> getBoletinsByCursoId(Integer idCurso) {
        List<Boletim> boletins = this.boletimRepository.getBoletinsByCursoId(idCurso);
        return boletins.stream().map(boletim -> this.boletimDTOofBoletim(boletim)).collect(Collectors.toList());
    }

    public List<BoletimDTO> getBoletinsByProfessorId(Integer idProfessor) {
        List<Boletim> boletins = this.boletimRepository.getBoletinsByProfessorId(idProfessor);
        return boletins.stream().map(boletim -> this.boletimDTOofBoletim(boletim)).collect(Collectors.toList());
    }

    public  Boletim boletimOfBoletimDTO(BoletimDTO boletimDTO) {
        Boletim boletim = new Boletim();
        AlunoDTO alunoDTO = this.alunoService.findAlunoDTO(boletimDTO.getIdAluno());
        ProfessorDTO professorDTO = this.professorService.findOptional(boletimDTO.getIdProfessor());
        CursoDTO cursoDTO = this.cursoService.findOptional(boletimDTO.getIdCurso());
        List<NotaDTO> notasDTO = boletimDTO.getNotas().stream().map(id -> this.notaService.find(id)).collect(Collectors.toList());
        List<Nota> notas = notasDTO.stream().map(nota -> this.notaService.notaOfDTO(nota)).collect(Collectors.toList());

        boletim.setId(boletimDTO.getId());
        boletim.setAno(boletimDTO.getAno());
        boletim.setAluno(this.alunoService.alunoOfAlunoDTO(alunoDTO));
        boletim.setProfessor(this.professorService.professorOfDTO(professorDTO));
        boletim.setCurso(this.cursoService.cursoOfCursoDTO(cursoDTO));
        boletim.setNotas(notas);
        return boletim;
    }

    public BoletimDTO boletimDTOofBoletim(Boletim boletim) {
        BoletimDTO boletimDTO = new BoletimDTO();
        List<Integer> notas = boletim.getNotas().stream().map(nota -> nota.getId()).collect(Collectors.toList());

        boletimDTO.setId(boletim.getId());
        boletimDTO.setAno(boletim.getAno());
        boletimDTO.setIdAluno(boletim.getAluno().getId());
        boletimDTO.setIdProfessor(boletim.getProfessor().getId());
        boletimDTO.setIdCurso(boletim.getCurso().getId());
        boletimDTO.setNotas(notas);
        return boletimDTO;
    }

    public BoletimListaDTO boletimListaDTOofBoletim(Boletim boletim) {
        BoletimListaDTO boletimListaDTO = new BoletimListaDTO();
        boletimListaDTO.setId(boletim.getId());
        boletimListaDTO.setAno(boletim.getAno());
        boletimListaDTO.setNomeAluno(boletim.getAluno().getName());
        boletimListaDTO.setNomeProfessor(boletim.getProfessor().getName());
        boletimListaDTO.setNomeCurso(boletim.getCurso().getName());
        return boletimListaDTO;
    }

    public BoletimPDFDTO boletimPDFDTOofBoletimDTO(BoletimDTO boletimDTO) {
        BoletimPDFDTO boletimPDFDTO = new BoletimPDFDTO();
        ProfessorDTO professorDTO = this.professorService.findOptional(boletimDTO.getIdProfessor());
        AlunoDTO alunoDTO = this.alunoService.findAlunoDTO(boletimDTO.getIdAluno());
        CursoDTO cursoDTO = this.cursoService.findOptional(boletimDTO.getIdCurso());
        List<NotaDTO> notasDTO = boletimDTO.getNotas().stream().map(id -> this.notaService.find(id)).collect(Collectors.toList());
        List<NotaPDFDTO> notas = notasDTO.stream().map(nota -> this.notaService.notaPDFDTOofnotaDTO(nota)).collect(Collectors.toList());

        boletimPDFDTO.setAno(boletimDTO.getAno());
        boletimPDFDTO.setProfessor(professorDTO.getName());
        boletimPDFDTO.setAluno(alunoDTO.getName());
        boletimPDFDTO.setCurso(cursoDTO.getName());
        boletimPDFDTO.setNotas(notas);
        return boletimPDFDTO;
    }

    public BoletimToEditDTO boletimToEditDTOofBoletim(Boletim boletim) {
        BoletimToEditDTO boletimEdit = new BoletimToEditDTO();
        boletimEdit.setId(boletim.getId());
        boletimEdit.setAno(boletim.getAno());
        boletimEdit.setIdAluno(boletim.getAluno().getId());
        boletimEdit.setIdProfessor(boletim.getProfessor().getId());
        boletimEdit.setIdCurso(boletim.getCurso().getId());
        List<NotaListDTO> notas = boletim.getNotas().stream().map(nota -> this.notaService.notaListDTOofNota(nota)).collect(Collectors.toList());
        boletimEdit.setNotas(notas);
        return boletimEdit;
    }
}
