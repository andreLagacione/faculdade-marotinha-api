package com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.service;

import com.lagacione.faculdademarotinhaapi.boletim.service.BoletimService;
import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.entity.MateriaNotaBimestre;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreDTO;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.model.MateriaNotaBimestreListDTO;
import com.lagacione.faculdademarotinhaapi.materia.service.MateriaService;
import com.lagacione.faculdademarotinhaapi.materiaNotaBimestre.repository.MatreriaNotaBimestreRespository;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
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
public class MateriaNotaBimestreService {
    private MatreriaNotaBimestreRespository matreriaNotaBimestreRespository;
    private MateriaService materiaService;
    private BoletimService boletimService;

    @Autowired
    public void MateriaNotaBimestreService(MatreriaNotaBimestreRespository matreriaNotaBimestreRespository, MateriaService materiaService, BoletimService boletimService) {
        this.matreriaNotaBimestreRespository = matreriaNotaBimestreRespository;
        this.materiaService = materiaService;
        this.boletimService = boletimService;
    }

    public List<MateriaNotaBimestreListDTO> findAll() {
        List<MateriaNotaBimestre> notas = this.matreriaNotaBimestreRespository.findAll();
        List<MateriaNotaBimestreListDTO> notasDTO = notas.stream().map(MateriaNotaBimestreListDTO::of).collect(Collectors.toList());
        return notasDTO;
    }

    public Page<MateriaNotaBimestreListDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<MateriaNotaBimestre> notas = this.matreriaNotaBimestreRespository.findAll(pageRequest);
        Page<MateriaNotaBimestreListDTO> notasDTO = notas.map(MateriaNotaBimestreListDTO::of);
        return notasDTO;
    }

    public MateriaNotaBimestreDTO find(Integer id) throws ObjectNotFoundException {
        Optional<MateriaNotaBimestre> nota = this.matreriaNotaBimestreRespository.findById(id);

        if (!nota.isPresent()) {
            throw new ObjectNotFoundException("Nota não encontrada!");
        }

        MateriaNotaBimestreDTO notaDTO = new MateriaNotaBimestreDTO();
        notaDTO = notaDTO.of(nota.get());
        return notaDTO;
    }

    private MateriaNotaBimestreDTO insert(MateriaNotaBimestre nota) {
        nota.setId(null);
        MateriaNotaBimestreDTO notaDTO = MateriaNotaBimestreDTO.of(this.matreriaNotaBimestreRespository.save(nota));
        this.boletimService.adicionarNotaBoletim(notaDTO);
        return notaDTO;
    }

    private MateriaNotaBimestreDTO update(MateriaNotaBimestre nota) throws ObjectNotFoundException {
        MateriaNotaBimestre newNota = MateriaNotaBimestre.of(this.find(nota.getId()));
        this.updateData(newNota, nota);
        MateriaNotaBimestreDTO notaDTO = MateriaNotaBimestreDTO.of(this.matreriaNotaBimestreRespository.save(nota));
        this.boletimService.alterarNotaBoletim(notaDTO);
        return notaDTO;
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            MateriaNotaBimestreDTO nota = this.find(id);
            this.matreriaNotaBimestreRespository.deleteById(id);
            this.boletimService.removerNotaBoletim(nota);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover esta nota!");
        }
    }

    private void updateData(MateriaNotaBimestre newNota, MateriaNotaBimestre nota) {
        newNota.setId(nota.getId());
        newNota.setMateria(nota.getMateria());
        newNota.setNotaBimestre1(nota.getNotaBimestre1());
        newNota.setNotaBimestre2(nota.getNotaBimestre2());
        newNota.setNotaBimestre3(nota.getNotaBimestre3());
        newNota.setNotaBimestre4(nota.getNotaBimestre4());
    }

    public MateriaNotaBimestreDTO salvarRegistro(MateriaNotaBimestreDTO notaDTO, Boolean adicionar) throws Exception {
        this.validarBoletim(notaDTO.getIdBoletim());
        this.validarMateria(notaDTO);
        this.obterNotasAdicionadas(notaDTO);

        notaDTO = this.calcularMediaFinal(notaDTO);
        MateriaNotaBimestre nota = MateriaNotaBimestre.of(notaDTO);

        if (adicionar) {
            return this.insert(nota);
        }

        return this.update(nota);
    }

    private void validarMateria(MateriaNotaBimestreDTO notaDTO) {
        Materia materia = Materia.of(notaDTO.getMateria());

        if (materia == null) {
            throw new ObjectNotFoundException("Informe a matéria!");
        }

        this.materiaService.find(materia.getId());
    }

    private void obterNotasAdicionadas(MateriaNotaBimestreDTO notaDTO) throws Exception {
        List<MateriaNotaBimestre> notas = this.matreriaNotaBimestreRespository.obterMateriaByIdBoletim(notaDTO.getIdBoletim());
        MateriaDTO materia = this.materiaService.find(notaDTO.getMateria().getId());

        if (notas != null || notas.size() > 0) {
            this.validarSeMateriaJaFoiAdicionada(notas, materia.getName());
        }
    }

    private void validarSeMateriaJaFoiAdicionada(List<MateriaNotaBimestre> notas, String nomeMateria) throws Exception {
        for (MateriaNotaBimestre nota : notas) {
            if (nota.getMateria().getName() == nomeMateria) {
                throw new Exception("A matéria " + nomeMateria + " já está cadastrada para este boletim!");
            }
        }
    }

    public void removerNotasBoletim(Integer idBoletim) {
        List<MateriaNotaBimestre> notas = this.matreriaNotaBimestreRespository.obterMateriaByIdBoletim(idBoletim);

        for (MateriaNotaBimestre nota : notas) {
            this.delete(nota.getId());
        }
    }

    private void validarBoletim(Integer idBoletim) {
        this.boletimService.findOptional(idBoletim);
    }

    private MateriaNotaBimestreDTO calcularMediaFinal(MateriaNotaBimestreDTO notaDTO) {
        Double notaBimestre1 = notaDTO.getNotaBimestre1();
        Double notaBimestre2 = notaDTO.getNotaBimestre2();
        Double notaBimestre3 = notaDTO.getNotaBimestre3();
        Double notaBimestre4 = notaDTO.getNotaBimestre4();

        if (notaBimestre1 != null && notaBimestre2 != null && notaBimestre3 != null && notaBimestre4 != null) {
            Double media = (notaBimestre1 + notaBimestre2 + notaBimestre3 + notaBimestre4) / 4;
            notaDTO.setMediaFinal(String.format("%.2f", media));
        }

        return notaDTO;
    }
}
