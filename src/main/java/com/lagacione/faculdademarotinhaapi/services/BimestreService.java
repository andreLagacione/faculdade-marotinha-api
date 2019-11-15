package com.lagacione.faculdademarotinhaapi.services;

import com.lagacione.faculdademarotinhaapi.domain.Bimestre;
import com.lagacione.faculdademarotinhaapi.dto.BimestreDTO;
import com.lagacione.faculdademarotinhaapi.repositories.BimestreRepository;
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
public class BimestreService {
    @Autowired
    private BimestreRepository bimestreRepository;

    public List<BimestreDTO> findAll() {
        List<Bimestre> bimestres = this.bimestreRepository.findAll();
        List<BimestreDTO> bimestresDTO = bimestres.stream().map(BimestreDTO::of).collect(Collectors.toList());
        return bimestresDTO;
    }

    public Page<BimestreDTO> findPage(Integer page, Integer size, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<Bimestre> bimestres = this.bimestreRepository.findAll(pageRequest);
        Page<BimestreDTO> bimestresDTO = bimestres.map(BimestreDTO::of);
        return bimestresDTO;
    }

    public Bimestre find(Integer id) throws ObjectNotFoundException {
        Optional<Bimestre> bimestre = this.bimestreRepository.findById(id);
        return bimestre.orElseThrow(() -> new ObjectNotFoundException("Bimestre não encontrado!"));
    }

    private Bimestre insert(Bimestre bimestre) {
        bimestre.setId(null);
        return this.bimestreRepository.save(bimestre);
    }

    private Bimestre update(Bimestre bimestre) throws ObjectNotFoundException {
        Bimestre newBimestre = this.find(bimestre.getId());
        this.updateData(newBimestre, bimestre);
        return this.bimestreRepository.save(newBimestre);
    }

    public void delete(Integer id) throws ObjectNotFoundException {
        this.find(id);

        try {
            this.bimestreRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível remover este bimestre!");
        }
    }

    private void updateData(Bimestre newBimestre, Bimestre bimestre) {
        newBimestre.setId(bimestre.getId());
        newBimestre.setAno(bimestre.getAno());
        newBimestre.setBimestre(bimestre.getBimestre());
    }

    public Bimestre salvarRegistro(BimestreDTO bimestreDTO, Boolean adicionar) throws Exception {
        Bimestre bimestre = Bimestre.of(bimestreDTO);

        if (adicionar) {
            return this.insert(bimestre);
        }

        return this.update(bimestre);
    }
}
