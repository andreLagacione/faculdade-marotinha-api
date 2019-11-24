package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.*;
import com.lagacione.faculdademarotinhaapi.dto.*;
import com.lagacione.faculdademarotinhaapi.repositories.BoletimRepository;
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
public class BoletimService {
    @Autowired
    private BoletimRepository boletimRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private MateriaNotaService materiaNotaService;

    @Autowired
    private CursoService cursoService;

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

    public BoletimFormDTO find(Integer id) throws ObjectNotFoundException {
        Optional<Boletim> boletim = this.boletimRepository.findById(id);

        if (boletim == null) {
            throw new ObjectNotFoundException("Boletim não encontrado!");
        }

        return BoletimFormDTO.of(boletim.get());
    }

    public Boletim findForUpdate(Integer id) throws ObjectNotFoundException {
        Optional<Boletim> boletim = this.boletimRepository.findById(id);
        return boletim.orElseThrow(() -> new ObjectNotFoundException("Boletim não encontrado!"));
    }

    private Boletim insert(Boletim boletim) {
        boletim.setId(null);
        return this.boletimRepository.save(boletim);
    }

    private Boletim update(Boletim boletim) throws ObjectNotFoundException {
        Boletim newBoletim = this.findForUpdate(boletim.getId());
        this.updateData(newBoletim, boletim);
        return this.boletimRepository.save(newBoletim);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.boletimRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover este boletim!");
        }
    }

    private void updateData(Boletim newBoletim, Boletim boletim) {
        newBoletim.setId(boletim.getId());
        newBoletim.setProfessor(boletim.getProfessor());
        newBoletim.setAluno(boletim.getAluno());
        newBoletim.setMateriaNotas(boletim.getMateriaNotas());
    }

    public Boletim salvarRegistro(BoletimDTO boletimDTO, Boolean adicionar) throws Exception {
        this.validarProfessor(boletimDTO);
        this.validarAluno(boletimDTO);
        this.validarMateriaNota(boletimDTO);

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

    private void validarMateriaNota(BoletimDTO boletimDTO) throws Exception {
        List<MateriaNotaDTO> materiaNotas = boletimDTO.getMateriaNotas();

        if (materiaNotas == null || materiaNotas.size() == 0) {
            throw new Exception("Informe uma matéria e a nota");
        }

        for (MateriaNotaDTO materiaNotaDTO : materiaNotas) {
            MateriaNota materiaNota = this.materiaNotaService.find(materiaNotaDTO.getId());

            if (materiaNota != null) {
                this.validarAlunoNota(materiaNota, boletimDTO.getAluno().getId());
            }
        }
    }

    private void validarAlunoNota(MateriaNota materiaNota, Integer idAlunoBoletim) throws Exception {
        Aluno aluno = materiaNota.getAluno();

        if (aluno.getId() != idAlunoBoletim) {
            Aluno alunoBoletim = this.alunoService.findOptional(idAlunoBoletim);
            Curso curso = materiaNota.getCurso();
            Bimestre bimestre = materiaNota.getBimestre();
            Materia materia = materiaNota.getMateria();
            Double nota = materiaNota.getNota();

            String mensagem = "A nota " + nota + " não pode ser atribuída para o aluno(a) ";
            mensagem += alunoBoletim.getName() + " pois ela já está atribuída para o aluno(a) ";
            mensagem += aluno.getName() + " na matéria ";
            mensagem += materia.getName() + " no curso ";
            mensagem += curso.getName() + " para o bimestre ";
            mensagem += bimestre.getBimestre() + "/" + bimestre.getAno();

            throw new Exception(mensagem);
        }
    }

    public GerarBoletimDTO gerarBoletin(ConsultaGerarBoletimDTO consultaGerarBoletim) {
        Integer idAluno = consultaGerarBoletim.getAlunoId();
        Integer idCurso = consultaGerarBoletim.getCursoId();
        Integer ano = consultaGerarBoletim.getAno();

        List<MateriaNota> notas = this.materiaNotaService.getNotaByAlunoAndCurso(idAluno, idCurso, ano);
        Aluno aluno = this.alunoService.findOptional(idAluno);
        Curso curso = this.cursoService.findForUpdate(idCurso);

        if (notas == null || notas.size() < 1) {
            String mensagem = "Não foram encontradas notas cadastradas para o aluno ";
            mensagem += aluno.getName() + " no curso " + curso.getName();
            mensagem += " no ano " + ano;

            throw new ObjectNotFoundException(mensagem);
        }

        Professor professor = this.professorService.findForUpdate(notas.get(0).getProfessor().getId());
        List<ListaNotaGerarBoletimDTO> notasBoletim = notas.stream().map(ListaNotaGerarBoletimDTO::of).collect(Collectors.toList());
        GerarBoletimDTO boletim = new GerarBoletimDTO(ano, aluno.getName(), curso.getName(), professor.getName(), notasBoletim);

        return boletim;
    }
}
