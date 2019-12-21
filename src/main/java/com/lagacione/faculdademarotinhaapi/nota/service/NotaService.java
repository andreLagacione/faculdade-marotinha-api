package com.lagacione.faculdademarotinhaapi.nota.service;

import com.lagacione.faculdademarotinhaapi.boletim.service.BoletimService;
import com.lagacione.faculdademarotinhaapi.commons.exceptions.ObjectNotFoundException;
import com.lagacione.faculdademarotinhaapi.materia.entity.Materia;
import com.lagacione.faculdademarotinhaapi.materia.model.MateriaDTO;
import com.lagacione.faculdademarotinhaapi.materia.service.MateriaService;
import com.lagacione.faculdademarotinhaapi.nota.entity.Nota;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaDTO;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaListDTO;
import com.lagacione.faculdademarotinhaapi.nota.model.NotaPDFDTO;
import com.lagacione.faculdademarotinhaapi.nota.repository.NotaRespository;
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
public class NotaService {
    private NotaRespository notaRespository;
    private MateriaService materiaService;
    private BoletimService boletimService;

    @Autowired
    public void MateriaNotaBimestreService(NotaRespository notaRespository, MateriaService materiaService, BoletimService boletimService) {
        this.notaRespository = notaRespository;
        this.materiaService = materiaService;
        this.boletimService = boletimService;
    }

    public List<NotaListDTO> findAll() {
        List<Nota> notas = this.notaRespository.findAll();
        List<NotaListDTO> notasDTO = notas.stream().map(nota -> this.notaListDTOofNota(nota)).collect(Collectors.toList());
        return notasDTO;
    }

    public Page<NotaListDTO> findPage(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        Page<Nota> notas = this.notaRespository.findAll(pageRequest);
        Page<NotaListDTO> notasDTO = notas.map(nota -> this.notaListDTOofNota(nota));
        return notasDTO;
    }

    public NotaDTO find(Integer id) throws ObjectNotFoundException {
        Optional<Nota> nota = this.notaRespository.findById(id);

        if (!nota.isPresent()) {
            throw new ObjectNotFoundException("Nota não encontrada!");
        }

        return this.notaDTOofNota(nota.get());
    }

    private NotaDTO insert(Nota nota) {
        nota.setId(null);
        NotaDTO notaDTO = this.notaDTOofNota(this.notaRespository.save(nota));
        this.boletimService.adicionarNotaBoletim(notaDTO);
        return notaDTO;
    }

    private NotaDTO update(Nota nota) throws ObjectNotFoundException {
        Nota newNota = this.notaOfDTO(this.find(nota.getId()));
        this.updateData(newNota, nota);
        NotaDTO notaDTO = this.notaDTOofNota(this.notaRespository.save(nota));
        this.boletimService.alterarNotaBoletim(notaDTO);
        return notaDTO;
    }

    public void delete(Integer id, Boolean boletimRemoved) throws ObjectNotFoundException {
        NotaDTO nota = this.find(id);

        try {
            if (!boletimRemoved) {
                this.boletimService.removerNotaBoletim(nota);
            }

            this.notaRespository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover esta nota!");
        }
    }

    private void updateData(Nota newNota, Nota nota) {
        newNota.setId(nota.getId());
        newNota.setMateria(nota.getMateria());
        newNota.setNotaBimestre1(nota.getNotaBimestre1());
        newNota.setNotaBimestre2(nota.getNotaBimestre2());
        newNota.setNotaBimestre3(nota.getNotaBimestre3());
        newNota.setNotaBimestre4(nota.getNotaBimestre4());
    }

    public NotaDTO salvarRegistro(NotaDTO notaDTO, Boolean adicionar) throws Exception {
        this.validarBoletim(notaDTO.getIdBoletim());
        this.validarMateria(notaDTO);
        this.obterNotasAdicionadas(notaDTO);

        notaDTO = this.calcularMediaFinal(notaDTO);
        Nota nota = this.notaOfDTO(notaDTO);

        if (adicionar) {
            return this.insert(nota);
        }

        return this.update(nota);
    }

    private void validarMateria(NotaDTO notaDTO) {
        Materia materia = this.materiaService.materiaOfMateriaDTO(notaDTO.getMateria());

        if (materia == null) {
            throw new ObjectNotFoundException("Informe a matéria!");
        }

        this.materiaService.find(materia.getId());
    }

    private void obterNotasAdicionadas(NotaDTO notaDTO) throws Exception {
        List<Nota> notas = this.notaRespository.obterMateriaByIdBoletim(notaDTO.getIdBoletim());
        MateriaDTO materia = this.materiaService.find(notaDTO.getMateria().getId());

        if (notas.size() > 0) {
            this.validarSeMateriaJaFoiAdicionada(notas, materia.getName());
        }
    }

    private void validarSeMateriaJaFoiAdicionada(List<Nota> notas, String nomeMateria) throws Exception {
        for (Nota nota : notas) {
            if (nota.getMateria().getName() == nomeMateria) {
                throw new Exception("A matéria " + nomeMateria + " já está cadastrada para este boletim!");
            }
        }
    }

    public void removerNotasBoletim(Integer idBoletim) {
        this.removeItensByList(this.notaRespository.obterMateriaByIdBoletim(idBoletim), false);
    }

    private void validarBoletim(Integer idBoletim) {
        this.boletimService.findBoletimDTO(idBoletim);
    }

    private NotaDTO calcularMediaFinal(NotaDTO notaDTO) {
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

    public List<NotaDTO> verificarSePodeRemoverNotas(Integer idMateria) {
        List<Nota> notas = this.notaRespository.obterNotasByIdMateria(idMateria);
        return notas.stream().map(nota -> this.notaDTOofNota(nota)).collect(Collectors.toList());
    }

    private void removeItensByList(List<Nota> notas, Boolean boletimRemoved) {
        for (Nota nota : notas) {
            this.delete(nota.getId(), boletimRemoved);
        }
    }

    public Nota notaOfDTO(NotaDTO notaDTO) {
        Nota nota = new Nota();
        nota.setId(notaDTO.getId());
        nota.setMateria(this.materiaService.materiaOfMateriaDTO(notaDTO.getMateria()));
        nota.setNotaBimestre1(notaDTO.getNotaBimestre1());
        nota.setNotaBimestre2(notaDTO.getNotaBimestre2());
        nota.setNotaBimestre3(notaDTO.getNotaBimestre3());
        nota.setNotaBimestre4(notaDTO.getNotaBimestre4());
        nota.setIdBoletim(notaDTO.getIdBoletim());
        nota.setMediaFinal(notaDTO.getMediaFinal());
        return nota;
    }

    public NotaDTO notaDTOofNota(Nota nota) {
        NotaDTO notaDTO = new NotaDTO();
        notaDTO.setId(nota.getId());
        notaDTO.setMateria(this.materiaService.materiaDTOofMateria(nota.getMateria()));
        notaDTO.setNotaBimestre1(nota.getNotaBimestre1());
        notaDTO.setNotaBimestre2(nota.getNotaBimestre2());
        notaDTO.setNotaBimestre3(nota.getNotaBimestre3());
        notaDTO.setNotaBimestre4(nota.getNotaBimestre4());
        notaDTO.setIdBoletim(nota.getIdBoletim());
        notaDTO.setMediaFinal(nota.getMediaFinal());
        return notaDTO;
    }

    public NotaPDFDTO notaPDFDTOofnotaDTO(NotaDTO notaDTO) {
        NotaPDFDTO notaPDFDTO = new NotaPDFDTO();
        notaPDFDTO.setId(notaDTO.getId());
        notaPDFDTO.setNomeMateria(notaDTO.getMateria().getName());
        notaPDFDTO.setNotaBimestre1(this.convertNota(notaDTO.getNotaBimestre1()));
        notaPDFDTO.setNotaBimestre2(this.convertNota(notaDTO.getNotaBimestre2()));
        notaPDFDTO.setNotaBimestre3(this.convertNota(notaDTO.getNotaBimestre3()));
        notaPDFDTO.setNotaBimestre4(this.convertNota(notaDTO.getNotaBimestre4()));
        notaPDFDTO.setIdBoletim(notaDTO.getIdBoletim());
        notaPDFDTO.setMediaFinal(notaDTO.getMediaFinal());
        return notaPDFDTO;
    }

    private String convertNota(Double nota) {
        if (nota == null) {
            return "N/A";
        }

        return String.format("%.2f", nota);
    }

    public NotaListDTO notaListDTOofNota(Nota nota) {
        NotaListDTO notaListDTO = new NotaListDTO();
        notaListDTO.setId(nota.getId());
        notaListDTO.setNomeMateria(nota.getMateria().getName());
        notaListDTO.setNotaBimestre1(nota.getNotaBimestre1());
        notaListDTO.setNotaBimestre2(nota.getNotaBimestre2());
        notaListDTO.setNotaBimestre3(nota.getNotaBimestre3());
        notaListDTO.setNotaBimestre4(nota.getNotaBimestre4());
        notaListDTO.setMediaFinal(nota.getMediaFinal());
        return notaListDTO;
    }
}
