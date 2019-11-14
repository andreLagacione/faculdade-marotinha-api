package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Boletim;
import com.lagacione.faculdademarotinhaapi.dto.BoletimDTO;
import com.lagacione.faculdademarotinhaapi.dto.MateriaNotaDTO;
import com.lagacione.faculdademarotinhaapi.repositories.BoletimRepository;
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
public class BoletimService {
    @Autowired
    private BoletimRepository boletimRepository;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private MateriaService materiaService;

    public List<BoletimDTO> findAll() {
        List<Boletim> boletins = this.boletimRepository.findAll();
        List<BoletimDTO> boletinsDTO = boletins.stream().map(BoletimDTO::of).collect(Collectors.toList());
        return boletinsDTO;
    }

    public Page<BoletimDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<Boletim> boletins = this.boletimRepository.findAll(pageRequest);
        Page<BoletimDTO> boletinsDTO = boletins.map(BoletimDTO::of);
        return boletinsDTO;
    }

    public Boletim find(Integer id) throws ObjectNotFoundException {
        Optional<Boletim> boletim = this.boletimRepository.findById(id);
        return boletim.orElseThrow(() -> new ObjectNotFoundException("Boletim não encontrado!"));
    }

    private Boletim insert(Boletim boletim) {
        boletim.setId(null);
        return this.boletimRepository.save(boletim);
    }

    private Boletim update(Boletim boletim) throws ObjectNotFoundException {
        Boletim newBoletim = this.find(boletim.getId());
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

        for (MateriaNotaDTO materiaNota : materiaNotas) {
            this.materiaService.find(materiaNota.getMateria().getId());
            this.validarNota(materiaNota.getNota());
        }
    }

    private void validarNota(Double nota) throws Exception {
        if (nota < 0 && nota > 10) {
            throw new Exception("A nota deve estar entre 0 e 10");
        }
    }
}
